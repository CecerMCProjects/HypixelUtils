package com.cecer1.hypixelutils.data;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigDataSourceValueStringList extends ConfigDataSourceValue<List<String>> {
    private List<String> _cachedValue;
    private String[] _defaults;

    public ConfigDataSourceValueStringList(String key, String[] defaults) {
        this.key = key;
        _defaults = defaults;
        _cachedValue = new ArrayList<String>();
        updateCachedValue();
    }

    @Override
    public void updateCachedValue() {
        String[] values = new Gson().fromJson(HypixelUtilsCore.internalConfig.getValue(key), String[].class);
        if(values == null)
            values = _defaults;
        
        _cachedValue = Arrays.asList(values);
    }
    
    /*
        MUST be called after changing the list or changes will not be saved.
     */
    public void storeChanges() {
        HypixelUtilsCore.internalConfig.setValue(key, new Gson().toJson(_cachedValue.toArray()));
    }

    @Override
    public List<String> getValue() {
        return _cachedValue;
    }

    @Override
    public void setValue(List<String> value) {
        HypixelUtilsCore.internalConfig.setValue(key, value.toString());
    }

    @Override
    public void setValueNoSave(List<String> value) {
        HypixelUtilsCore.internalConfig.setValueNoEvents(key, value.toString());
    }
}