package com.cecer1.hypixelutils;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class UtilityMethods {
    public static boolean isCurrentServerHypixel() {
        return true; // TODO: Actually check this.
    }

    public static IChatComponent getHypixelUtilsChatComponentPrefix() {
        return new ChatComponentText("")
                .appendSibling(new ChatComponentText("[").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)))
                .appendSibling(new ChatComponentText("HypixelUtils").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)))
                .appendSibling(new ChatComponentText("] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)));
    }
}
