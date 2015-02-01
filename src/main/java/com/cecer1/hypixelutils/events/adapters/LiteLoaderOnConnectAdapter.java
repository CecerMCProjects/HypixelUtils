package com.cecer1.hypixelutils.events.adapters;

import com.cecer1.hypixelutils.events.EventAdapter;
import com.cecer1.hypixelutils.events.EventManager;
import com.cecer1.hypixelutils.events.eventdata.OnConnectEventData;
import net.minecraft.client.multiplayer.ServerData;

public class LiteLoaderOnConnectAdapter extends EventAdapter {
    public LiteLoaderOnConnectAdapter(EventManager manager) {
        super(manager);
    }

    public boolean trigger(ServerData serverData) {
        OnConnectEventData eventData = new OnConnectEventData(serverData);
        manager.fireEvent(eventData);
        return !eventData.isCanceled();
    }
}
