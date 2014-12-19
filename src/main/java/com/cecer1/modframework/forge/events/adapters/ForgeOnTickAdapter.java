package com.cecer1.modframework.forge.events.adapters;

import com.cecer1.modframework.common.events.EventAdapter;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.forge.events.ForgeOnTickEventData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class ForgeOnTickAdapter extends EventAdapter {

    public ForgeOnTickAdapter(EventManager manager) {
        super(manager);
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START)
            return;

        ForgeOnTickEventData eventData = new ForgeOnTickEventData(event);
        manager.fireEvent(eventData);
    }
}
