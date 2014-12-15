package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.HypixelUtils;
import com.cecer1.hypixelutils.Utility;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.world.WorldEvent;

public class LobbyAutoSwapProcessor extends BaseBungeeMoveServerProcessor {

    private static final long SWAPLOBBY_DELAY_TICKS = 40; // If this delay is not here then the client apparently crashes.

    public LobbyAutoSwapProcessor()
    {
        super();
    }

    public LobbyAutoSwapProcessor(Property configProperty, boolean enabledByDefault)
    {
        super(configProperty, enabledByDefault);
    }

    private int _desiredLobbyNumber;

    public void setDesiredLobbyNumber(int desiredLobbyNumber) {
        _desiredLobbyNumber = desiredLobbyNumber;
    }

    @SubscribeEvent
    public void internalOnChat(ClientChatReceivedEvent event)
    {
        if(isEnabledAtAll())
            onChat(event);
    }

    public void onChat(ClientChatReceivedEvent event) {
        if(_desiredLobbyNumber < 0)
            return;

        IChatComponent rootComponent = Utility.getRootChatComponent(event.message);
        if(Utility.compareChatComponent(rootComponent, "{\"color\":\"aqua\",\"text\":\"You are currently on server \"}"))
        {
            event.setCanceled(true);
            final int desiredLobby = _desiredLobbyNumber;
            _desiredLobbyNumber = -1;

            IChatComponent locationComponent = (IChatComponent) event.message.getSiblings().get(0);
            String location = locationComponent.getUnformattedText();
            if(location.contains("lobby")) {
                int number = Integer.parseInt(location.split("lobby")[1]);

                if(number == desiredLobby) // If we're already here then we don't need to do anything.
                    return;

                HypixelUtils.instance.scheduler.scheduleTask(new Runnable() {
                    @Override
                    public void run() {
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/swaplobby " + desiredLobby);
                    }
                }, SWAPLOBBY_DELAY_TICKS);

            }
        }
    }

    @Override
    public void onBungeeMoveServer(WorldEvent.Load event) {
        if(_desiredLobbyNumber > 0)
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/whereami");
    }
}
