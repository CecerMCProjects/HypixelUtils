package com.cecer1.hypixelutils.data;

import com.cecer1.hypixelutils.HypixelUtilsCore;

public class ConfigDataSourceValueInteger extends ConfigDataSourceValue<Integer> {
    private int _cachedValueP;
    private Integer _cachedValueC = null;

    public ConfigDataSourceValueInteger(String key) {
        this.key = key;
        updateCachedValue();
    }

    @Override
    public void updateCachedValue() {
        String stringValue = HypixelUtilsCore.internalConfig.getValue(key);
        if(stringValue != null) {
            _cachedValueC = Integer.parseInt(stringValue);
            _cachedValueP = _cachedValueC.intValue();
        }
    }

    @Override
    public Integer getValue() {
        return _cachedValueC;
    }
    public int getValue(int fallbackValue) {
        if(_cachedValueC == null)
            return fallbackValue;
        return _cachedValueP;
    }

    @Override
    public void setValue(Integer value) {
        HypixelUtilsCore.internalConfig.setValue(key, value.toString());
    }
    public void setValue(int value) {
        HypixelUtilsCore.internalConfig.setValue(key, Integer.toString(value));
    }

    @Override
    public void setValueNoSave(Integer value) {
        HypixelUtilsCore.internalConfig.setValueNoEvents(key, value.toString());
    }
    public void setValueNoSave(int value) {
        HypixelUtilsCore.internalConfig.setValueNoEvents(key, Integer.toString(value));
    }
}
