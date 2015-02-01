package com.cecer1.hypixelutils.events.eventdata;

import net.minecraft.util.IChatComponent;

public class OnChatEventData implements IEventData {
    private IChatComponent _message;
    private boolean _canceled;

    public OnChatEventData(IChatComponent message) {
        _message = message;
    }

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
