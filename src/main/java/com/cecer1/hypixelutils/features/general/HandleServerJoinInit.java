package com.cecer1.hypixelutils.features.general;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.features.cloudconfig.CloudConfigManager;
import com.cecer1.hypixelutils.gui.GuiConfigManagerWrapper;
import com.cecer1.modframework.common.events.IOnConnectEventHandler;

public class HandleServerJoinInit implements IOnConnectEventHandler {

    @Override
    public void onConnect(IOnConnectEventData event) {
        if(HypixelUtilsCore.currentState.isConnected()) {
            ChatOutputs.printHypixelDetected(HypixelUtilsCore.VERSION);

            if(HypixelUtilsCore.config.isDebugModeEnabled()) {
                CloudConfigManager typedConfig;
                
                if(!(HypixelUtilsCore.config instanceof CloudConfigManager)) {
                    if(!(HypixelUtilsCore.config instanceof GuiConfigManagerWrapper)) {
                        if(((GuiConfigManagerWrapper)HypixelUtilsCore.config).getBackingConfig() instanceof CloudConfigManager) {
                            typedConfig = (CloudConfigManager) ((GuiConfigManagerWrapper) HypixelUtilsCore.config).getBackingConfig();
                        } else {
                            typedConfig = null;
                        }
                    } else {
                        typedConfig = null;
                    }
                } else {
                    typedConfig = (CloudConfigManager) HypixelUtilsCore.config;
                }
                if(typedConfig == null)
                    return;

                ChatOutputs.printDebugCurrentSavedConfigKey(HypixelUtilsCore.getSavedConfigKey());
            }
        }
    }
}
