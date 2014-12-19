package com.cecer1.modframework.forge.events;

import com.cecer1.modframework.common.events.IEventData;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ForgeOnChatEventData implements IOnChatEventHandler.IOnChatEventData {
    private ClientChatReceivedEvent _backingEvent;

    public ForgeOnChatEventData(ClientChatReceivedEvent event) {
        _backingEvent = event;
    }

    @Override
    public IChatComponent getMessage() {
        return _backingEvent.message;
    }

    @Override
    public boolean isCanceled() {
        return _backingEvent.isCanceled();
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        _backingEvent.setCanceled(cancel);
        return this;
    }

}
