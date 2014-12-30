package com.cecer1.hypixelutils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class UtilityMethods {

    public static boolean isHypixel() {
        String currentServer = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        if(currentServer.endsWith(".hypixel.net"))
            return true;
        if(currentServer.contains(".hypixel.net:"))
            return true;
        return false;
    }

    public static IChatComponent getHypixelUtilsChatComponentPrefix() {
        return new ChatComponentText("")
                .appendSibling(new ChatComponentText("[").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)))
                .appendSibling(new ChatComponentText("HypixelUtils").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)))
                .appendSibling(new ChatComponentText("] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)));
    }

    public static IChatComponent getHypixelUtilsChatComponentDebugPrefix() {
        return new ChatComponentText("")
                .appendSibling(new ChatComponentText("[").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)))
                .appendSibling(new ChatComponentText("HypixelUtils").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)))
                .appendSibling(new ChatComponentText(" Debug").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_RED)))
                .appendSibling(new ChatComponentText("] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)));
    }
}
