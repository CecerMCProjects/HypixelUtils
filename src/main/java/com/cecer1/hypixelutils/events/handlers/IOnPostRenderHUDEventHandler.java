package com.cecer1.hypixelutils.events.handlers;

import com.cecer1.hypixelutils.events.eventdata.OnPostRenderHUDEventData;

public interface IOnPostRenderHUDEventHandler extends IEventHandler {
    public void onEvent(OnPostRenderHUDEventData data);
}
