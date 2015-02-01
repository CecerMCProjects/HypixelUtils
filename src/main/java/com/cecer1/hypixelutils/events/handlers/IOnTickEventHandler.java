package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnTickEventData;

public interface IOnTickEventHandler extends IEventHandler {
    public void onEvent(OnTickEventData data);
}
