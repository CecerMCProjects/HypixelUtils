package com.cecer1.hypixelutils.backgroundcommands;

import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnChatMessageEventHandler;

public abstract class HypixelCommandJob implements IOnChatEventHandler, IOnChatMessageEventHandler {
    public abstract void trigger();

    public abstract boolean isDone();

    public abstract void complete();
    
    public abstract Cooldown getCooldown();

    public abstract boolean isStarted();
    
    public void timedOut() {

    }
    
    public boolean isTimedOut() {
        return false;
    }
}
