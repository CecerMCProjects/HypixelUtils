package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;

public interface IOnChatEventHandler extends IEventHandler {
    public void onEvent(OnChatEventData data);
}
