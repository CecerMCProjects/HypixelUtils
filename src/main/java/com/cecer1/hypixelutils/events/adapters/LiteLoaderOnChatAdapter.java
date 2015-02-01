package com.cecer1.hypixelutils.events.adapters;

import com.cecer1.hypixelutils.events.EventAdapter;
import com.cecer1.hypixelutils.events.EventManager;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker;
import net.minecraft.util.IChatComponent;

public class LiteLoaderOnChatAdapter extends EventAdapter {
    public LiteLoaderOnChatAdapter(EventManager manager) {
        super(manager);
    }

    public boolean trigger(IChatComponent chat, String message, LiteLoaderEventBroker.ReturnValue<IChatComponent> newMessage) {
        OnChatEventData eventData = new OnChatEventData(chat);
        manager.fireEvent(eventData);

        return !eventData.isCanceled();
    }
}
