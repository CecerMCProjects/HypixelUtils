package com.cecer1.hypixelutils.features.improvedlobby;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.features.general.HypixelStateUpdateType;
import com.cecer1.hypixelutils.features.general.IOnHypixelStateUpdatedEventHandler;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;

public class ImprovedLobbyCommandProcessor implements IOnHypixelStateUpdatedEventHandler, IOnChatEventHandler
{
    private static final long SWAPLOBBY_DELAY_TICKS = 40; // If this delay is not here then the client apparently crashes.

    private int _desiredLobbyNumber;

    public void setDesiredLobbyNumber(int desiredLobbyNumber) {
        _desiredLobbyNumber = desiredLobbyNumber;
    }

    @Override
    public void onHypixelStateUpdated(IOnHypixelStateUpdatedEventData eventData) {
        if(eventData.getUpdateType() == HypixelStateUpdateType.SERVER_NAME) {
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
    public void onChat(IOnChatEventData event) {
        if(_desiredLobbyNumber != 0) {
            if(ChatUtilities.compareChatComponent(event.getMessage(), "{\"color\":\"red\",\"text\":\"You are already connected to this server\"}")) {
                event.setCanceled(true);
                HypixelUtilsCore.currentState.updateServerName();
            }
        }
    }
}
