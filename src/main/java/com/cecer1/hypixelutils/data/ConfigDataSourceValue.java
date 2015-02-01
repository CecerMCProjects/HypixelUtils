package com.cecer1.hypixelutils.data;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigDirtyEventData;
import com.cecer1.hypixelutils.events.handlers.IOnConfigDirtyEventHandler;

public abstract class ConfigDataSourceValue<T> extends DataSourceValue<T> implements IOnConfigDirtyEventHandler {
    public String key;
    
    public ConfigDataSourceValue() {
        HypixelUtilsCore.eventManager.registerEventHandlers(this);
    }

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnConfigDirtyEventData)
            onEvent((OnConfigDirtyEventData)data);
    }
    
    @Override
    public void onEvent(OnConfigDirtyEventData data) {
        if(data.getDirtyKey() == null || data.getDirtyKey().equals(key))
            updateCachedValue();
    }
    
    public abstract void updateCachedValue();
    
    public abstract T getValue();
    public abstract void setValue(T value);
    public abstract void setValueNoSave(T value);
}