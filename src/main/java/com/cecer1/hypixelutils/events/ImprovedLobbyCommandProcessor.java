package com.cecer1.hypixelutils.events;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.events.IOnBungeeServerChangeEventHandler;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

public class ImprovedLobbyCommandProcessor implements IOnChatEventHandler, IOnBungeeServerChangeEventHandler
{
    private static final long SWAPLOBBY_DELAY_TICKS = 40; // If this delay is not here then the client apparently crashes.

    private int _desiredLobbyNumber;

    public void setDesiredLobbyNumber(int desiredLobbyNumber) {
        _desiredLobbyNumber = desiredLobbyNumber;
    }

    @Override
    public void onChat(IOnChatEventData event) {
        if(_desiredLobbyNumber < 0)
            return;

        IChatComponent rootComponent = ChatUtilities.getRootChatComponent(event.getMessage());
        if(ChatUtilities.compareChatComponent(rootComponent, "{\"color\":\"aqua\",\"text\":\"You are currently on server \"}"))
        {
            event.setCanceled(true);
            final int desiredLobby = _desiredLobbyNumber;
            _desiredLobbyNumber = -1;

            IChatComponent locationComponent = (IChatComponent) event.getMessage().getSiblings().get(0);
            String location = locationComponent.getUnformattedText();
            if(location.contains("lobby")) {
                int number = Integer.parseInt(location.split("lobby")[1]);

                if(number == desiredLobby) // If we're already here then we don't need to do anything.
                    return;

                HypixelUtilsCore.scheduler.scheduleTask(new Runnable() {
                    @Override
                    public void run() {
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/swaplobby " + desiredLobby);
                    }
                }, SWAPLOBBY_DELAY_TICKS);

            }
        }
    }

    @Override
    public void onBungeeServerChange(IOnBungeeServerChangeEventData eventData) {
        if(_desiredLobbyNumber > 0)
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/whereami");
    }
}
