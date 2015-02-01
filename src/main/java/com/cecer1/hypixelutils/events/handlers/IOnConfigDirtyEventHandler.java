package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnConfigDirtyEventData;

public interface IOnConfigDirtyEventHandler extends IEventHandler {
    public void onEvent(OnConfigDirtyEventData data);
}
