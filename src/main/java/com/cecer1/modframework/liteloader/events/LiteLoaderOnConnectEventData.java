package com.cecer1.modframework.liteloader.events;

import com.cecer1.modframework.common.events.IEventData;
import com.cecer1.modframework.common.events.IOnConnectEventHandler;
import net.minecraft.client.multiplayer.ServerData;

public class LiteLoaderOnConnectEventData implements IOnConnectEventHandler.IOnConnectEventData {
    private ServerData _serverData;
    private boolean _canceled;

    public LiteLoaderOnConnectEventData(ServerData serverData) {
        _serverData = serverData;
    }

    @Override
    public ServerData getServerData() {
        return _serverData;
    }

    @Override
    public boolean isCanceled() {
        return _canceled;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        _canceled = cancel;
        return this;
    }
}
