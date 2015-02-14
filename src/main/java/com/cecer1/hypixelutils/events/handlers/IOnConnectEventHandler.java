package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnJoinGameEventData;

public interface IOnConnectEventHandler extends IEventHandler {
    public void onEvent(OnJoinGameEventData data);
}
