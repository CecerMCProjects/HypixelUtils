package com.cecer1.hypixelutils.features.improvedlobby;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnHypixelStateUpdatedEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnHypixelStateUpdatedEventHandler;
import com.cecer1.hypixelutils.features.general.HypixelStateUpdateType;
import com.cecer1.hypixelutils.utils.ChatUtilities;

public class ImprovedLobbyCommandProcessor implements IOnHypixelStateUpdatedEventHandler, IOnChatEventHandler
{
    private static final long SWAPLOBBY_DELAY_TICKS = 40; // If this delay is not here then the client apparently crashes.

    private int _desiredLobbyNumber;

    public void setDesiredLobbyNumber(int desiredLobbyNumber) {
        _desiredLobbyNumber = desiredLobbyNumber;
    }

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnHypixelStateUpdatedEventData)
            onEvent((OnHypixelStateUpdatedEventData)data);
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
    }

    @Override
    public void onEvent(OnHypixelStateUpdatedEventData data) {
        if(data.getUpdateType() == HypixelStateUpdateType.SERVER_NAME) {
            if(_desiredLobbyNumber > 0) {
                String location = HypixelUtilsCore.currentState.getCurrentServerName();

                final int desiredLobby = _desiredLobbyNumber;
                _desiredLobbyNumber = 0;

                if (location.contains("lobby")) {
                    int number = Integer.parseInt(location.split("lobby")[1]);

                    if (number == desiredLobby) // If we're already here then we don't need to do anything.
                        return;

                    HypixelUtilsCore.scheduler.scheduleTask(new Runnable() {
                        @Override
                        public void run() {
                            HypixelUtilsCore.sendChatMessage("/swaplobby " + desiredLobby);
                        }
                    }, SWAPLOBBY_DELAY_TICKS);

                }
            }
        }
    }

    @Override
    public void onEvent(OnChatEventData data) {
        if(_desiredLobbyNumber != 0) {
            if(ChatUtilities.compareChatComponent(data.getMessage(), "{\"color\":\"red\",\"text\":\"You are already connected to this server\"}")) {
                data.setCanceled(true);
                HypixelUtilsCore.currentState.updateServerName();
            }
        }
    }
}
