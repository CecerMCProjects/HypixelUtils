package com.cecer1.hypixelutils.features.general;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnJoinGameEventData;
import com.cecer1.hypixelutils.events.handlers.IOnConnectEventHandler;

public class HandleServerJoinInit implements IOnConnectEventHandler {
    
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnJoinGameEventData)
            onEvent((OnJoinGameEventData)data);
    }

    @Override
    public void onEvent(OnJoinGameEventData data) {
        if(HypixelUtilsCore.currentState.isConnected()) {
            ChatOutputs.printHypixelDetected(HypixelUtilsCore.VERSION);

            if(HypixelUtilsCore.configHelper.debugModeEnabled.getValue(false)) {
                ChatOutputs.printDebugCurrentSavedConfigKey(HypixelUtilsCore.getSavedConfigKey());
            }
        }
    }
}
