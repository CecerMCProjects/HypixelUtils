package com.cecer1.modframework.common.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

public class ChatUtilities {
    public static class ChatPresets {
        public static final ChatStyle BLACK = new ChatStyle().setColor(EnumChatFormatting.BLACK);
        public static final ChatStyle DARK_BLUE = new ChatStyle().setColor(EnumChatFormatting.DARK_BLUE);
        public static final ChatStyle DARK_GREEN = new ChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
        public static final ChatStyle DARK_AQUA = new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA);
        public static final ChatStyle DARK_RED = new ChatStyle().setColor(EnumChatFormatting.DARK_RED);
        public static final ChatStyle DARK_PURPLE = new ChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
        public static final ChatStyle GOLD = new ChatStyle().setColor(EnumChatFormatting.GOLD);
        public static final ChatStyle GRAY = new ChatStyle().setColor(EnumChatFormatting.GRAY);
        public static final ChatStyle DARK_GRAY = new ChatStyle().setColor(EnumChatFormatting.DARK_GRAY);
        public static final ChatStyle BLUE = new ChatStyle().setColor(EnumChatFormatting.BLUE);
        public static final ChatStyle GREEN = new ChatStyle().setColor(EnumChatFormatting.GREEN);
        public static final ChatStyle AQUA = new ChatStyle().setColor(EnumChatFormatting.AQUA);
        public static final ChatStyle RED = new ChatStyle().setColor(EnumChatFormatting.RED);
        public static final ChatStyle LIGHT_PURPLE = new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE);
        public static final ChatStyle YELLOW = new ChatStyle().setColor(EnumChatFormatting.YELLOW);
        public static final ChatStyle WHITE = new ChatStyle().setColor(EnumChatFormatting.WHITE);
    }
    
    public static boolean isCurrentServerHypixel()
    {
        return true; // TODO: Actually check this
    }

    public static boolean compareChatComponent(IChatComponent first, IChatComponent second)
    {
        return convertChatComponentToJson(first).equals(convertChatComponentToJson(second));
    }
    public static boolean compareChatComponent(IChatComponent component, String jsonString)
    {
        IChatComponent jComponent = convertJsonToChatComponent(jsonString);
        return compareChatComponent(component, jComponent);
    }

    public static IChatComponent getRootChatComponent(IChatComponent component)
    {
        IChatComponent rootChatComponent = component.createCopy();
        rootChatComponent.getSiblings().clear();
        return rootChatComponent;
    }


    public static List<IChatComponent> getFlattenedChatComponent(IChatComponent component) {
        return getFlattenedChatComponent(component, false);
    }

    public static List<IChatComponent> getFlattenedChatComponent(IChatComponent component, boolean removeEmpty)
    {
        if(removeEmpty && isEmptyChatComponent(component))
            return null;

        List<IChatComponent> components = new ArrayList<IChatComponent>();
        if(component.getSiblings().size() == 0) {
            components.add(component);
            return components;
        }

        components.add(getRootChatComponent(component));
        for(Object sibling : component.getSiblings()) {
            List<IChatComponent> flattenedSiblings = getFlattenedChatComponent((IChatComponent) sibling, removeEmpty);
            if(flattenedSiblings != null) {
                components.addAll(flattenedSiblings);
            }
        }
        return components;
    }

    private static final IChatComponent emptyChatComponent = new ChatComponentText("");
    public static boolean isEmptyChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, emptyChatComponent);
    }


    /**
     * This method is for MC 1.7.10 only.
     */
    public static String convertChatComponentToJson(IChatComponent component) {
        return IChatComponent.Serializer.componentToJson(component);
    }

    /**
     * This method is for MC 1.7.10 only.
      */
    public static IChatComponent convertJsonToChatComponent(String json) {
        return IChatComponent.Serializer.jsonToComponent(json);
    }

    public static void printChatComponent(IChatComponent component) {
        if(Minecraft.getMinecraft().thePlayer == null)
            return;

        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(component);
    }

    public static void sendChat(String message) {
        if(Minecraft.getMinecraft().thePlayer == null)
            return;

        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }
}
