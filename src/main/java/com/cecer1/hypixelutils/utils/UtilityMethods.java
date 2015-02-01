package com.cecer1.hypixelutils.utils;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import static com.cecer1.hypixelutils.utils.ChatUtilities.QuickFormatting.*;

public class UtilityMethods {
    private static IChatComponent _prefix = new ChatComponentText("")
            .appendSibling(darkAqua(new ChatComponentText("[")))
            .appendSibling(white(new ChatComponentText("HypixelUtils")))
            .appendSibling(darkAqua(new ChatComponentText("] ")));
    public static IChatComponent getHypixelUtilsChatComponentPrefix() {
        return _prefix;
    }

    private static IChatComponent _debugPrefix = new ChatComponentText("")
            .appendSibling(darkAqua(new ChatComponentText("[")))
            .appendSibling(white(new ChatComponentText("HypixelUtils")))
            .appendSibling(red(new ChatComponentText(" Debug")))
            .appendSibling(darkAqua(new ChatComponentText("] ")));
    public static IChatComponent getHypixelUtilsChatComponentDebugPrefix()
    {
        return _debugPrefix;
    }
}
