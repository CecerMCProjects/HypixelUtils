package com.cecer1.hypixelutils.features.general;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConnectEventData;
import com.cecer1.hypixelutils.events.handlers.IOnConnectEventHandler;

public class HandleServerJoinInit implements IOnConnectEventHandler {
    
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnConnectEventData)
            onEvent((OnConnectEventData)data);
    }

    @Override
    public void onEvent(OnConnectEventData data) {
        if(HypixelUtilsCore.currentState.isConnected()) {
            ChatOutputs.printHypixelDetected(HypixelUtilsCore.VERSION);

            if(HypixelUtilsCore.configHelper.debugModeEnabled.getValue(false)) {
                ChatOutputs.printDebugCurrentSavedConfigKey(HypixelUtilsCore.getSavedConfigKey());
            }
        }
    }
}
