package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnHypixelStateUpdatedEventData;

public interface IOnHypixelStateUpdatedEventHandler extends IEventHandler {
    public void onEvent(OnHypixelStateUpdatedEventData data);
}
