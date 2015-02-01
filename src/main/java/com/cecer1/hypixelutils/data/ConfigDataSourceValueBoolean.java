package com.cecer1.hypixelutils.data;

import com.cecer1.hypixelutils.HypixelUtilsCore;

public class ConfigDataSourceValueBoolean extends ConfigDataSourceValue<Boolean> {
    private boolean _cachedValueP;
    private Boolean _cachedValueC;

    public ConfigDataSourceValueBoolean(String key) {
        this.key = key;
        updateCachedValue();
    }

    @Override
    public void updateCachedValue() {
        _cachedValueC = Boolean.parseBoolean(HypixelUtilsCore.internalConfig.getValue(key));
        if(_cachedValueC != null)
            _cachedValueP = _cachedValueC.booleanValue();
    }

    @Override
    public Boolean getValue() {
        return _cachedValueC;
    }
    public boolean getValue(boolean fallbackValue) {
        if(_cachedValueC == null)
            return fallbackValue;
        return _cachedValueP;
    }

    @Override
    public void setValue(Boolean value) {
        HypixelUtilsCore.internalConfig.setValue(key, value.toString());
    }
    public void setValue(boolean value) {
        HypixelUtilsCore.internalConfig.setValue(key, Boolean.toString(value));
    }

    @Override
    public void setValueNoSave(Boolean value) {
        HypixelUtilsCore.internalConfig.setValueNoEvents(key, value.toString());
    }
    public void setValueNoSave(boolean value) {
        HypixelUtilsCore.internalConfig.setValueNoEvents(key, Boolean.toString(value));
    }
}
