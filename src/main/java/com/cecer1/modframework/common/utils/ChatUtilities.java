package com.cecer1.modframework.common.utils;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

public class ChatUtilities {
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
}
