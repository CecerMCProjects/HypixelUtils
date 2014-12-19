package com.cecer1.modframework.forge.events;

import com.cecer1.modframework.common.events.IEventData;
import com.cecer1.modframework.common.events.IOnTickEventHandler;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ForgeOnTickEventData implements IOnTickEventHandler.IOnTickEventData {
    private TickEvent.ClientTickEvent _backingEvent;

    public ForgeOnTickEventData(TickEvent.ClientTickEvent event) {
        _backingEvent = event;
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
