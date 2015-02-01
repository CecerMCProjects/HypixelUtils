package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnConnectEventData;

public interface IOnConnectEventHandler extends IEventHandler {
    public void onEvent(OnConnectEventData data);
}
