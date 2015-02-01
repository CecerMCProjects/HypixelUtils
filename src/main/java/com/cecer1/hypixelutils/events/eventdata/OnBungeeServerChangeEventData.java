package com.cecer1.hypixelutils.events.eventdata;

public class OnBungeeServerChangeEventData implements IEventData {

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        return null;
    }
}
