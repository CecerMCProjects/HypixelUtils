package com.cecer1.hypixelutils.data;

import com.cecer1.hypixelutils.HypixelUtilsCore;

public class ConfigDataSourceValueString extends ConfigDataSourceValue<String> {
    private String _cachedValue;
    
    public ConfigDataSourceValueString(String key) {
        this.key = key;
        updateCachedValue();
    }

    @Override
    public void updateCachedValue() {
        _cachedValue = HypixelUtilsCore.internalConfig.getValue(key);
    }

    @Override
    public String getValue() {
        return _cachedValue;
    }
    @Override
    public void setValue(String value) {
        HypixelUtilsCore.internalConfig.setValue(key, value);
    }

    @Override
    public void setValueNoSave(String value) {
        HypixelUtilsCore.internalConfig.setValueNoEvents(key, value);
    }
}
