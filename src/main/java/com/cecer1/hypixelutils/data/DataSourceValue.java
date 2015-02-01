package com.cecer1.hypixelutils.data;

public abstract class DataSourceValue<T> {
    public abstract void updateCachedValue();
    
    public abstract T getValue();
    public abstract void setValue(T value);
}