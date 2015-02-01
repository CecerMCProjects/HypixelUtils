package com.cecer1.hypixelutils.events.eventdata;

public class OnConfigUpdatedEventData implements IEventData {

    private String _key;
    public OnConfigUpdatedEventData(String updatedKey) {
        _key = updatedKey;
    }

    public String getUpdatedKey() {
        return _key;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        return this;
    }

    public boolean isUpdatedKey(String key) {
        return _key == null || _key.equals(key);
    }
}
