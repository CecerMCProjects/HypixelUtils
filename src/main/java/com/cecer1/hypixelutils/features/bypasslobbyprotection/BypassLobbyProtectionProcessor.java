package com.cecer1.hypixelutils.features.bypasslobbyprotection;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnConfigUpdatedEventHandler;
import com.cecer1.hypixelutils.utils.ChatUtilities;

public class BypassLobbyProtectionProcessor implements IOnChatEventHandler, IOnConfigUpdatedEventHandler
{
    private boolean isEnabled()
    {
        if(!HypixelUtilsCore.currentState.isConnected())
            return false;

        return HypixelUtilsCore.configHelper.bypassLobbyProtectionEnabled.getValue(false);
    }

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
        if(data instanceof OnConfigUpdatedEventData)
            onEvent((OnConfigUpdatedEventData)data);
    }

    @Override
    public void onEvent(OnChatEventData data) {
        if(!isEnabled())
            return;

        if (ChatUtilities.compareChatComponent(data.getMessage(), "{\"color\":\"green\",\"text\":\"Are you sure? Type /lobby again if you really want to quit.\"}"))
        {
            HypixelUtilsCore.sendChatMessage("/lobby");
            data.setCanceled(true);
        }
    }

    @Override
    public void onEvent(OnConfigUpdatedEventData data) {
        String key = "bypassprotection.lobbycommand.enabled";
        if(data.isUpdatedKey(key)) {
            ChatOutputs.printBypassLobbyProtectionEnabledStatus(HypixelUtilsCore.configHelper.bypassLobbyProtectionEnabled.getValue(false));
        }
    }
}