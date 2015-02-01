package com.cecer1.hypixelutils.features.debug;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;
import com.cecer1.hypixelutils.events.eventdata.OnHypixelStateUpdatedEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnConfigUpdatedEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnHypixelStateUpdatedEventHandler;
import com.cecer1.hypixelutils.features.general.HypixelStateUpdateType;
import com.cecer1.hypixelutils.utils.ChatUtilities;

public class DebugProcessor implements IOnChatEventHandler, IOnHypixelStateUpdatedEventHandler, IOnConfigUpdatedEventHandler
{
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
        if(data instanceof OnHypixelStateUpdatedEventData)
            onEvent((OnHypixelStateUpdatedEventData)data);
        if(data instanceof OnConfigUpdatedEventData)
            onEvent((OnConfigUpdatedEventData)data);
   }

    @Override
    public void onEvent(OnChatEventData data) {
        if(!HypixelUtilsCore.configHelper.debugModeEnabled.getValue(false))
            return;

        System.out.println(ChatUtilities.convertChatComponentToJson(data.getMessage()));
    }

    @Override
    public void onEvent(OnHypixelStateUpdatedEventData data) {
        if(data.getUpdateType() == HypixelStateUpdateType.SERVER_NAME) {
            ChatOutputs.printDebugServerNameSetTo(HypixelUtilsCore.currentState.getCurrentServerName());
        } else if(data.getUpdateType() == HypixelStateUpdateType.MAP_NAME) {
            ChatOutputs.printDebugMapNameSetTo(HypixelUtilsCore.currentState.getCurrentMapName());
        } else if(data.getUpdateType() == HypixelStateUpdateType.PROXY_NAME) {
            ChatOutputs.printDebugProxyNameSetTo(HypixelUtilsCore.currentState.getCurrentProxyName());
        }
    }

    @Override
    public void onEvent(OnConfigUpdatedEventData data) {
        String key = "debug.debugchat.enabled";
        if(data.isUpdatedKey(key)) {
            ChatOutputs.printDebugDebugModeEnabledStatus(HypixelUtilsCore.configHelper.debugModeEnabled.getValue(false));
        }
    }
}