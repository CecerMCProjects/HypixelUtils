package com.cecer1.modframework.common.events;

import net.minecraft.util.IChatComponent;

public interface IOnChatEventHandler extends IEventHandler {
    public void onChat(IOnChatEventData event);

    static interface IOnChatEventData extends IEventData {
        public IChatComponent getMessage();
    }
}
