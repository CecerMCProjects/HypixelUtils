package com.cecer1.modframework.liteloader.events;

import com.cecer1.modframework.common.events.IEventData;
import com.cecer1.modframework.common.events.IOnRenderEventHandler;

public class LiteLoaderOnRenderEventData implements IOnRenderEventHandler.IOnRenderEventData {
    
    private boolean _inGame;
    public LiteLoaderOnRenderEventData(boolean inGame) {
        _inGame = inGame;
    }
    
    @Override
    public boolean isIngame() {
        return _inGame;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        return null;
    }
}
