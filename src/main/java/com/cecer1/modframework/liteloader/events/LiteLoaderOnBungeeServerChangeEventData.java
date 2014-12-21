package com.cecer1.modframework.liteloader.events;

import com.cecer1.modframework.common.events.IEventData;
import com.cecer1.modframework.common.events.IOnBungeeServerChangeEventHandler;

public class LiteLoaderOnBungeeServerChangeEventData implements IOnBungeeServerChangeEventHandler.IOnBungeeServerChangeEventData {

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        return null;
    }
}
