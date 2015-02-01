package com.cecer1.hypixelutils.features.filterpartychat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.chat.ChatType;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatMessageEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatMessageEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnConfigUpdatedEventHandler;

public class FilterPartyChatProcessor implements IOnChatMessageEventHandler, IOnConfigUpdatedEventHandler
{
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatMessageEventData)
            onEvent((OnChatMessageEventData)data);
        if(data instanceof OnConfigUpdatedEventData)
            onEvent((OnConfigUpdatedEventData)data);
    }

    @Override
    public void onEvent(OnChatMessageEventData data) {
        if(HypixelUtilsCore.configHelper.filterPartyChatEnabled.getValue(false))
            data.setCanceled(data.chatTypes.contains(ChatType.PARTY));
    }

    @Override
    public void onEvent(OnConfigUpdatedEventData data) {
        String key = "party.filterchat.enabled";
        if(data.isUpdatedKey(key)) {
            ChatOutputs.printFilterPartyChatEnabledStatus(HypixelUtilsCore.configHelper.filterPartyChatEnabled.getValue(false));
        }
    }
}