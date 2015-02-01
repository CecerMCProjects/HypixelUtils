package com.cecer1.hypixelutils.features.instantbed;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.backgroundcommands.HypixelCommandJobBypassedLobby;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnConfigUpdatedEventHandler;
import com.cecer1.hypixelutils.utils.ChatUtilities;

public class InstantBedProcessor implements IOnChatEventHandler, IOnConfigUpdatedEventHandler
{
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData) data);
        if(data instanceof OnConfigUpdatedEventData)
            onEvent((OnConfigUpdatedEventData)data);
    }
    
    @Override
    public void onEvent(OnChatEventData data) {
        if(!HypixelUtilsCore.configHelper.instantBedEnabled.getValue(false))
            return;

        if (ChatUtilities.compareChatComponent(data.getMessage(), "{\"extra\":[{\"bold\":true,\"color\":\"green\",\"text\":\"Teleporting you to the lobby in 3 seconds... Right-click again to cancel the teleport!\"}],\"text\":\"\"}"))
        {
            HypixelUtilsCore.commandJobManager.queueJob(new HypixelCommandJobBypassedLobby());
            data.setCanceled(true);
        }
    }

    @Override
    public void onEvent(OnConfigUpdatedEventData data) {
        String key = "bypassprotection.lobbybed.enabled";
        if(data.isUpdatedKey(key)) {
            ChatOutputs.printInstantBedEnabledStatus(HypixelUtilsCore.configHelper.instantBedEnabled.getValue(false));
        }
    }
}
