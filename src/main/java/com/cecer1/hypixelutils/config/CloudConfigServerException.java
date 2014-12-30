package com.cecer1.hypixelutils.config;

import com.cecer1.hypixelutils.UtilityMethods;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class CloudConfigServerException extends Exception {
    public CloudConfigServerException(String message) {
        super(message);
    }

    public void printToChat(String friendlyErrorPrefix) {
        this.printStackTrace();

        if(Minecraft.getMinecraft().thePlayer == null)
            return; // Minecraft isn't done loading yet. Nothing left to do.

        if(Minecraft.getMinecraft().getCurrentServerData() == null)
            return; // Minecraft isn't done loading yet. Nothing left to do.

        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("ERROR: ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_RED)))
            .appendSibling(new ChatComponentText(friendlyErrorPrefix).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED))));

        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(UtilityMethods.getHypixelUtilsChatComponentPrefix()
                .appendSibling(new ChatComponentText("Cause: ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)))
                .appendSibling(new ChatComponentText(this.getMessage()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE))));
    }
}
