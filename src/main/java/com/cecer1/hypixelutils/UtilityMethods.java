package com.cecer1.hypixelutils;

import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class UtilityMethods {

    public static IChatComponent getHypixelUtilsChatComponentPrefix() {
        return new ChatComponentText("")
                .appendSibling(new ChatComponentText("[").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA))
                .appendSibling(new ChatComponentText("HypixelUtils").setChatStyle(ChatUtilities.ChatPresets.WHITE))
                .appendSibling(new ChatComponentText("] ").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA));
    }

    public static IChatComponent getHypixelUtilsChatComponentDebugPrefix() {
        return new ChatComponentText("")
                .appendSibling(new ChatComponentText("[").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA))
                .appendSibling(new ChatComponentText("HypixelUtils").setChatStyle(ChatUtilities.ChatPresets.WHITE))
                .appendSibling(new ChatComponentText(" Debug").setChatStyle(ChatUtilities.ChatPresets.DARK_RED))
                .appendSibling(new ChatComponentText("] ").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA));
    }
}
