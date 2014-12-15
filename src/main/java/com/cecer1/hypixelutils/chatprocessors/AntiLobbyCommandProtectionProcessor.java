package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.Utility;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;

public class AntiLobbyCommandProtectionProcessor extends BaseChatProcessor
{
    public AntiLobbyCommandProtectionProcessor(Property property, boolean enabledByDefault) {
        super(property, enabledByDefault);
    }

    @Override
    public void onChat(ClientChatReceivedEvent event)
    {
        if (Utility.compareChatComponent(event.message, "{\"color\":\"green\",\"text\":\"Are you sure? Type /lobby again if you really want to quit.\"}"))
        {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby");
            event.setCanceled(true);
        }
    }
}
