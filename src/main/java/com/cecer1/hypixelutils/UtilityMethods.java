package com.cecer1.hypixelutils;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class UtilityMethods
{
    public static boolean isCurrentServerHypixel()
    {
        return true; // TODO: Actually check this
    }

    public static boolean compareChatComponent(IChatComponent first, IChatComponent second)
    {
        return AbstractedSrgMethods.convertChatComponentToJson(first).equals(AbstractedSrgMethods.convertChatComponentToJson(second));
    }
    public static boolean compareChatComponent(IChatComponent component, String jsonString)
    {
        IChatComponent jComponent = AbstractedSrgMethods.convertJsonToChatComponent(jsonString);
        return compareChatComponent(component, jComponent);
    }

    public static IChatComponent getRootChatComponent(IChatComponent component)
    {
        IChatComponent rootChatComponent = component.createCopy();
        rootChatComponent.getSiblings().clear();
        return rootChatComponent;
    }

    public static IChatComponent getHypixelUtilsChatComponentPrefix() {
        return new ChatComponentText("")
                .appendSibling(new ChatComponentText("[").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)))
                .appendSibling(new ChatComponentText("HypixelUtils").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)))
                .appendSibling(new ChatComponentText("] ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)));
    }
}
