package com.cecer1.hypixelutils.events.eventdata;

public class OnRenderEventData implements IEventData {

    public OnRenderEventData() {}

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        return null;
    }
}
