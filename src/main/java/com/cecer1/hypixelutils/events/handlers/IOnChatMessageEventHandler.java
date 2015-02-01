package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnChatMessageEventData;

public interface IOnChatMessageEventHandler extends IEventHandler {
    public void onEvent(OnChatMessageEventData data);
}
