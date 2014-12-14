package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.UtilityMethods;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
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
        if (UtilityMethods.compareChatComponent(event.message, "{\"color\":\"green\",\"text\":\"Are you sure? Type /lobby again if you really want to quit.\"}"))
        {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby");
            event.setCanceled(true);

            IChatComponent notificationMessage = UtilityMethods.getHypixelUtilsChatComponentPrefix()
                    .appendSibling(new ChatComponentText("/lobby was automatically confirmed.").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
            Minecraft.getMinecraft().thePlayer.addChatMessage(notificationMessage);
        }
    }
}
