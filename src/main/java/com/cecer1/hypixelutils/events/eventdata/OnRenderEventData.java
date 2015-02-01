package com.cecer1.hypixelutils.events.eventdata;

public class OnRenderEventData implements IEventData {
    
    private boolean _inGame;
    public OnRenderEventData(boolean inGame) {
        _inGame = inGame;
    }

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
