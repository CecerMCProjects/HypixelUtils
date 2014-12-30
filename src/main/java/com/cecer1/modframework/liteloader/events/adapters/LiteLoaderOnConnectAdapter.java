package com.cecer1.modframework.liteloader.events.adapters;

import com.cecer1.modframework.common.events.EventAdapter;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.liteloader.events.LiteLoaderOnConnectEventData;
import net.minecraft.client.multiplayer.ServerData;

public class LiteLoaderOnConnectAdapter extends EventAdapter {
    public LiteLoaderOnConnectAdapter(EventManager manager) {
        super(manager);
    }

    public boolean trigger(ServerData serverData) {
        LiteLoaderOnConnectEventData eventData = new LiteLoaderOnConnectEventData(serverData);
        manager.fireEvent(eventData);
        return !eventData.isCanceled();
    }
}
