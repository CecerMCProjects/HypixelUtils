package com.cecer1.hypixelutils.features.debug;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.hypixelutils.features.general.HypixelStateUpdateType;
import com.cecer1.hypixelutils.features.general.IOnHypixelStateUpdatedEventHandler;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class DebugProcessor implements IOnChatEventHandler, IOnHypixelStateUpdatedEventHandler
{
    @Override
    public void onChat(IOnChatEventData event) {
        if(!HypixelUtilsCore.config.isDebugModeEnabled())
            return;

        System.out.println(ChatUtilities.convertChatComponentToJson(event.getMessage()));
    }

    @Override
    public void onHypixelStateUpdated(IOnHypixelStateUpdatedEventData eventData) {
        if(eventData.getUpdateType() == HypixelStateUpdateType.SERVER_NAME) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
                    .appendSibling(new ChatComponentText("Server Name set to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
                    .appendSibling(new ChatComponentText(HypixelUtilsCore.currentState.getCurrentServerName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN))));
        } else if(eventData.getUpdateType() == HypixelStateUpdateType.MAP_NAME) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
                    .appendSibling(new ChatComponentText("Map Name set to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
                    .appendSibling(new ChatComponentText(HypixelUtilsCore.currentState.getCurrentMapName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN))));
        } else if(eventData.getUpdateType() == HypixelStateUpdateType.PROXY_NAME) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
                    .appendSibling(new ChatComponentText("Proxy set to ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
                    .appendSibling(new ChatComponentText(HypixelUtilsCore.currentState.getCurrentProxyName()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN))));
        }
    }
}