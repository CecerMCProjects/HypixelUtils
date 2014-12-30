package com.cecer1.modframework.common.events;

import net.minecraft.client.multiplayer.ServerData;

public interface IOnConnectEventHandler extends IEventHandler {
    public void onConnect(IOnConnectEventData event);

    static interface IOnConnectEventData extends IEventData {
        public ServerData getServerData();
    }
}
