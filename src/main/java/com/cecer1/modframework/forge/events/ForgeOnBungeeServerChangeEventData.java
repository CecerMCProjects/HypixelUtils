package com.cecer1.modframework.forge.events;

import com.cecer1.modframework.common.events.IEventData;
import com.cecer1.modframework.common.events.IOnBungeeServerChangeEventHandler;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ForgeOnBungeeServerChangeEventData implements IOnBungeeServerChangeEventHandler.IOnBungeeServerChangeEventData {

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        return this;
    }
}
