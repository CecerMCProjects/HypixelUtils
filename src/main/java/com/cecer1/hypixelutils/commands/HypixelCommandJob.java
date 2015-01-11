package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.chat.ChatMessage;
import com.cecer1.hypixelutils.chat.IChatMessageSubscriber;
import com.cecer1.modframework.common.events.IOnChatEventHandler;

public abstract class HypixelCommandJob implements IChatMessageSubscriber, IOnChatEventHandler {
    @Override
    public abstract boolean processChatMessage(ChatMessage message);

    @Override
    public abstract void onChat(IOnChatEventData event);

    public abstract void trigger();

    public abstract boolean isDone();

    public abstract void complete();
    
    public abstract Cooldown getCooldown();

    public abstract boolean isStarted();
}
