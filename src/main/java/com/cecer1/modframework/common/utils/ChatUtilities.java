package com.cecer1.modframework.common.utils;

import net.minecraft.util.IChatComponent;

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
