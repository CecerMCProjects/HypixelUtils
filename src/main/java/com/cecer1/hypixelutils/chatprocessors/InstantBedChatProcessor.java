package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.HypixelUtils;
import com.cecer1.hypixelutils.Utility;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;

public class InstantBedChatProcessor extends BaseChatProcessor
{
    public InstantBedChatProcessor()
    {
        super();
    }

    public InstantBedChatProcessor(Property configProperty, boolean enabledByDefault)
    {
        super(configProperty, enabledByDefault);
    }

    @Override
    public void onChat(ClientChatReceivedEvent event)
    {
        if (Utility.compareChatComponent(event.message, "{\"extra\":[{\"bold\":true,\"color\":\"green\",\"text\":\"Teleporting you to the lobby in 3 seconds... Right-click again to cancel the teleport!\"}],\"text\":\"\"}"))
        {
            HypixelUtils.instance.antiLobbyCommandProtectionProcessor.enableOnce();
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby");
            event.setCanceled(true);
        }
    }
}
