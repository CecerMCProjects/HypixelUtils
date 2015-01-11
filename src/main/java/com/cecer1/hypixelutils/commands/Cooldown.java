package com.cecer1.hypixelutils.commands;

import com.cecer1.modframework.common.events.IOnTickEventHandler;

public class Cooldown implements IOnTickEventHandler{
    private int _current;
    private int _cooldown;
    
    public Cooldown(int cooldown) {
        _cooldown = cooldown;
    }
    
    @Override
    public void onTick(IOnTickEventData event) {
        if(_current > 0)
            _current--;
    }

    public boolean isCooling() {
        return _current != 0;
    }

    public void reset() {
        _current = _cooldown;
    }
}
