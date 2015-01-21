package com.cecer1.modframework.liteloader.events.adapters;

import com.cecer1.modframework.common.events.EventAdapter;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.liteloader.events.LiteLoaderOnRenderEventData;
import net.minecraft.client.Minecraft;

public class LiteLoaderOnRenderAdapter extends EventAdapter {
    public LiteLoaderOnRenderAdapter(EventManager manager) {
        super(manager);
    }

    public void trigger(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
        LiteLoaderOnRenderEventData eventData = new LiteLoaderOnRenderEventData(inGame);
        manager.fireEvent(eventData);
    }
}
