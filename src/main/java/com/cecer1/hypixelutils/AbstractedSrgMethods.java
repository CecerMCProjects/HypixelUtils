package com.cecer1.hypixelutils;

import net.minecraft.util.IChatComponent;

// This class is for MC 1.7.10.
public class AbstractedSrgMethods {
    public static String convertChatComponentToJson(IChatComponent component) {
        return IChatComponent.Serializer.func_150696_a(component);
    }
    public static IChatComponent convertJsonToChatComponent(String json) {
        return IChatComponent.Serializer.func_150699_a(json);
    }
}
