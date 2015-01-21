package com.cecer1.hypixelutils.features.debug;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.features.general.HypixelStateUpdateType;
import com.cecer1.hypixelutils.features.general.IOnHypixelStateUpdatedEventHandler;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;

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
            ChatOutputs.printDebugServerNameSetTo(HypixelUtilsCore.currentState.getCurrentServerName());
        } else if(eventData.getUpdateType() == HypixelStateUpdateType.MAP_NAME) {
            ChatOutputs.printDebugMapNameSetTo(HypixelUtilsCore.currentState.getCurrentMapName());
        } else if(eventData.getUpdateType() == HypixelStateUpdateType.PROXY_NAME) {
            ChatOutputs.printDebugProxyNameSetTo(HypixelUtilsCore.currentState.getCurrentProxyName());
        }
    }
}