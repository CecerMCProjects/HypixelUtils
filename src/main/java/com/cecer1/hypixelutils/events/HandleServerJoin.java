package com.cecer1.hypixelutils.events;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.hypixelutils.config.CloudConfigManager;
import com.cecer1.modframework.common.events.IOnConnectEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class HandleServerJoin implements IOnConnectEventHandler {

    @Override
    public void onConnect(IOnConnectEventData event) {
        if(UtilityMethods.isHypixel()) {
            IChatComponent welcomeMessage = UtilityMethods.getHypixelUtilsChatComponentPrefix()
                    .appendSibling(new ChatComponentText("Hypixel Network detected! You are running HypixelUtils version ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
                    .appendSibling(new ChatComponentText(HypixelUtilsCore.VERSION).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_AQUA)))
                    .appendSibling(new ChatComponentText(".").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(welcomeMessage);

            if(HypixelUtilsCore.config.isDebugModeEnabled()) {
                CloudConfigManager typedConfig = (CloudConfigManager) HypixelUtilsCore.config;

                IChatComponent debugMessage = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
                    .appendSibling(new ChatComponentText("Current saved config key is ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)))
                    .appendSibling(new ChatComponentText(typedConfig.getConfigKey().toString()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)))
                    .appendSibling(new ChatComponentText("!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(debugMessage);
            }
        }
    }
}
