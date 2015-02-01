package com.cecer1.hypixelutils.data.config;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;
import com.cecer1.hypixelutils.events.handlers.IOnConfigUpdatedEventHandler;

public class ConfigManager implements IOnConfigUpdatedEventHandler{
    public final ConfigStore configStore;
    
    public ConfigManager(ConfigStore store) {
        configStore = store;
        HypixelUtilsCore.eventManager.registerEventHandlers(this);
    }
    
    public String getValue(String key) {
        return configStore.getRawValue(key);
    }

    public void setValue(String key, String value) {
        configStore.setRawValue(key, value);
    }
    public void setValueNoEvents(String key, String value) {
        configStore.setRawValueNoEvents(key, value);
    }

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnConfigUpdatedEventData)
            onEvent((OnConfigUpdatedEventData)data);
    }
    @Override
    public void onEvent(OnConfigUpdatedEventData data) {
        HypixelUtilsCore.configHelper.save();
    }
}
