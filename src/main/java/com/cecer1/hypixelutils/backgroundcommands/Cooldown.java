package com.cecer1.hypixelutils.backgroundcommands;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnTickEventData;
import com.cecer1.hypixelutils.events.handlers.IOnTickEventHandler;

public class Cooldown implements IOnTickEventHandler {
    private int _current;
    private int _cooldown;
    
    public Cooldown(int cooldown) {
        _cooldown = cooldown;
        
        if(_cooldown > 0)
            HypixelUtilsCore.eventManager.registerEventHandlers(this);
    }

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnTickEventData)
            onEvent((OnTickEventData)data);
    }

    @Override
    public void onEvent(OnTickEventData data) {
        if(_current > 0)
            _current--;
    }
    
    public boolean isCooling() {
        return _current > 0;
    }

    public void reset() {
        _current = _cooldown;
    }
}
