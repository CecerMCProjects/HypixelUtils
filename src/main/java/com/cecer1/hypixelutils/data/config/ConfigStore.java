package com.cecer1.hypixelutils.data.config;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.OnConfigDirtyEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;

import java.util.Map;

public class ConfigStore {
    private Map<String, String> _storage;
    
    public ConfigStore(Map<String, String> initialValues) {
        _storage = initialValues;
    }
    
    public String getRawValue(String key) {
        if(_storage.containsKey(key))
            return _storage.get(key);
        return null;
    }
    public void setRawValue(String key, String value) {
        boolean newValue = !_storage.containsKey(key) || !_storage.get(key).equals(value);
        _storage.put(key, value);
        if(newValue) {
            HypixelUtilsCore.eventManager.fireEvent(new OnConfigDirtyEventData(key));
            HypixelUtilsCore.eventManager.fireEvent(new OnConfigUpdatedEventData(key));
        }
    }
    public void setRawValueNoEvents(String key, String value) {
        _storage.put(key, value);
    }
    public boolean contains(String key) {
        return _storage.containsKey(key);
    }
    public Map<String, String> getAll() {
        return _storage;
    }
    public void clear() {
        _storage.clear();
    }
}