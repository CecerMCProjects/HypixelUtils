package com.cecer1.hypixelutils.events.eventdata;

public class OnConfigDirtyEventData implements IEventData {

    private String _key;
    public OnConfigDirtyEventData(String dirtyKey) {
        _key = dirtyKey;
    }

    public String getDirtyKey() {
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

    public boolean isDirtyKey(String key) {
        return _key == null || _key.equals(key);
    }
}
