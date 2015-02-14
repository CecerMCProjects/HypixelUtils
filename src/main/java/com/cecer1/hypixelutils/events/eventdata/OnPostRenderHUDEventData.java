package com.cecer1.hypixelutils.events.eventdata;

public class OnPostRenderHUDEventData implements IEventData {
    private int _screenWidth;
    private int _ScreenHeight;
    
    public OnPostRenderHUDEventData(int screenWidth, int screenHeight) {
        _screenWidth = screenWidth;
        _ScreenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return _screenWidth;
    }

    public int getScreenHeight() {
        return _ScreenHeight;
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
