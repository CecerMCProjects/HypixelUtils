package com.cecer1.modframework.liteloader.events;

import com.cecer1.modframework.common.events.IEventData;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import net.minecraft.util.IChatComponent;

public class LiteLoaderOnChatEventData implements IOnChatEventHandler.IOnChatEventData {
    private IChatComponent _message;
    private boolean _canceled;

    public LiteLoaderOnChatEventData(IChatComponent message) {
        _message = message;
    }

    @Override
    public IChatComponent getMessage() {
        return _message;
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
