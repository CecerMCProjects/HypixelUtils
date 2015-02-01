package com.cecer1.hypixelutils.backgroundcommands;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatMessageEventData;

// TODO: Make this backgroundable and confirm it actually had a successful result.
public class HypixelCommandJobTip extends HypixelCommandJob {
    private boolean _started = false;
    private boolean _done = false;
    private String _playerName = null;

    public HypixelCommandJobTip(String playerName) {
        _playerName = playerName;
    }
    
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
        if(data instanceof OnChatMessageEventData)
            onEvent((OnChatMessageEventData)data);
    }


    @Override
    public void onEvent(OnChatEventData data) {

    }
    
    @Override
    public void onEvent(OnChatMessageEventData data) {

    }
    @Override
    public void trigger() {
        HypixelUtilsCore.sendChatMessage("/tip " + _playerName);
        _started = true;
        _done = true;
    }

    @Override
    public boolean isStarted() {
        return _started;
    }

    @Override
    public boolean isDone() {
        return _done;
    }

    @Override
    public void complete() {
    }

    @Override
    public Cooldown getCooldown() {
        return HypixelUtilsCore.commandJobManager.boosterCommandCooldown;
    }
}
