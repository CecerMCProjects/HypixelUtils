package com.cecer1.modframework.liteloader.events;

import com.cecer1.modframework.common.events.IEventData;
import com.cecer1.modframework.common.events.IOnRenderEventHandler;

public class LiteLoaderOnRenderEventData implements IOnRenderEventHandler.IOnRenderEventData {
    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        return null;
    }
}
