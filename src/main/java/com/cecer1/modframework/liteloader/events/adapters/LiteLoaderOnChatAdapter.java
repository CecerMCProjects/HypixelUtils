package com.cecer1.modframework.liteloader.events.adapters;

import com.cecer1.modframework.common.events.EventAdapter;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.liteloader.events.LiteLoaderOnChatEventData;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker;
import net.minecraft.util.IChatComponent;

public class LiteLoaderOnChatAdapter extends EventAdapter {
    public LiteLoaderOnChatAdapter(EventManager manager) {
        super(manager);
    }

    public boolean trigger(IChatComponent chat, String message, LiteLoaderEventBroker.ReturnValue<IChatComponent> newMessage) {
        LiteLoaderOnChatEventData eventData = new LiteLoaderOnChatEventData(chat);
        manager.fireEvent(eventData);

        return !eventData.isCanceled();
    }
}
