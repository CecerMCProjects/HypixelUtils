package com.cecer1.hypixelutils.events.eventdata;

import net.minecraft.client.multiplayer.ServerData;

public class OnConnectEventData implements IEventData {
    private ServerData _serverData;
    private boolean _canceled;

    public OnConnectEventData(ServerData serverData) {
        _serverData = serverData;
    }

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
