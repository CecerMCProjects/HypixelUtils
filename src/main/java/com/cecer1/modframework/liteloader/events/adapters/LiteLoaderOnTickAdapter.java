package com.cecer1.modframework.liteloader.events.adapters;

import com.cecer1.modframework.common.events.EventAdapter;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.liteloader.events.LiteLoaderOnTickEventData;
import net.minecraft.client.Minecraft;

public class LiteLoaderOnTickAdapter extends EventAdapter {
    public LiteLoaderOnTickAdapter(EventManager manager) {
        super(manager);
    }

    public void trigger(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
        if(!clock)
            return;

        LiteLoaderOnTickEventData eventData = new LiteLoaderOnTickEventData();
        manager.fireEvent(eventData);
    }
}
