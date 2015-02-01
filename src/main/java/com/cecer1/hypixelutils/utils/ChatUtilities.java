package com.cecer1.hypixelutils.utils;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.ArrayList;
import java.util.List;

public class ChatUtilities {
    public static class QuickFormatting {
        public static IChatComponent setColor(IChatComponent component, EnumChatFormatting color) {
            component.getChatStyle().setColor(color);
            return component;
        }
        public static IChatComponent black(IChatComponent component) {
            return setColor(component, EnumChatFormatting.BLACK);
        }
        public static IChatComponent darkBlue(IChatComponent component) {
            return setColor(component, EnumChatFormatting.DARK_BLUE);
        }
        public static IChatComponent darkGreen(IChatComponent component) {
            return setColor(component, EnumChatFormatting.DARK_GREEN);
        }
        public static IChatComponent darkAqua(IChatComponent component) {
            return setColor(component, EnumChatFormatting.DARK_AQUA);
        }
        public static IChatComponent darkRed(IChatComponent component) {
            return setColor(component, EnumChatFormatting.DARK_RED);
        }
        public static IChatComponent darkPurple(IChatComponent component) {
            return setColor(component, EnumChatFormatting.DARK_PURPLE);
        }
        public static IChatComponent gold(IChatComponent component) {
            return setColor(component, EnumChatFormatting.GOLD);
        }
        public static IChatComponent gray(IChatComponent component) {
            return setColor(component, EnumChatFormatting.GRAY);
        }
        public static IChatComponent darkGray(IChatComponent component) {
            return setColor(component, EnumChatFormatting.DARK_GRAY);
        }
        public static IChatComponent blue(IChatComponent component) {
            return setColor(component, EnumChatFormatting.BLUE);
        }
        public static IChatComponent green(IChatComponent component) {
            return setColor(component, EnumChatFormatting.GREEN);
        }
        public static IChatComponent aqua(IChatComponent component) {
            return setColor(component, EnumChatFormatting.AQUA);
        }
        public static IChatComponent red(IChatComponent component) {
            return setColor(component, EnumChatFormatting.RED);
        }
        public static IChatComponent lightPurple(IChatComponent component) {
            return setColor(component, EnumChatFormatting.LIGHT_PURPLE);
        }
        public static IChatComponent yellow(IChatComponent component) {
            return setColor(component, EnumChatFormatting.YELLOW);
        }
        public static IChatComponent white(IChatComponent component) {
            return setColor(component, EnumChatFormatting.WHITE);
        }
        public static IChatComponent bold(IChatComponent component) {
            component.getChatStyle().setBold(true);
            return component;
        }
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

    public static void printRawChatComponent(IChatComponent component) {
        if(Minecraft.getMinecraft().thePlayer == null)
            return;

        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(component);
    }
    public static void printChatComponent(IChatComponent component) {
        if(Minecraft.getMinecraft().thePlayer == null)
            return;

        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("").appendSibling(UtilityMethods.getHypixelUtilsChatComponentPrefix()).appendSibling(component));
    }
    public static void printDebugChatComponent(IChatComponent component) {
        printDebugChatComponent(component, false);
    }
    public static void printDebugChatComponent(IChatComponent component, boolean forceShow) {
        if(Minecraft.getMinecraft().thePlayer == null)
            return;
        if(!forceShow && !HypixelUtilsCore.configHelper.debugModeEnabled.getValue(false))
            return;

        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("").appendSibling(UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()).appendSibling(component));
    }

    public static void sendChat(String message) {
        if(Minecraft.getMinecraft().thePlayer == null)
            return;

        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }
}
