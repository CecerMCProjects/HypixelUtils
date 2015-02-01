package com.cecer1.hypixelutils.events.adapters;

import com.cecer1.hypixelutils.events.EventAdapter;
import com.cecer1.hypixelutils.events.EventManager;
import com.cecer1.hypixelutils.events.eventdata.OnTickEventData;
import net.minecraft.client.Minecraft;

public class LiteLoaderOnTickAdapter extends EventAdapter {
    public LiteLoaderOnTickAdapter(EventManager manager) {
        super(manager);
    }

    public void trigger(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
        if(!clock)
            return;

        OnTickEventData eventData = new OnTickEventData();
        manager.fireEvent(eventData);
    }
}
