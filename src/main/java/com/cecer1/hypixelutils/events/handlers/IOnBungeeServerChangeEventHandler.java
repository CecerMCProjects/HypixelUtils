package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnBungeeServerChangeEventData;

public interface IOnBungeeServerChangeEventHandler extends IEventHandler {
    public void onEvent(OnBungeeServerChangeEventData data);
}
