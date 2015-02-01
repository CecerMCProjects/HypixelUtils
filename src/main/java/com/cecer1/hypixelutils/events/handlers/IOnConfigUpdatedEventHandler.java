package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;

public interface IOnConfigUpdatedEventHandler extends IEventHandler {
    public void onEvent(OnConfigUpdatedEventData data);
}
