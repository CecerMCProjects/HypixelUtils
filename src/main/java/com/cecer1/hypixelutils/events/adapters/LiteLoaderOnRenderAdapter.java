package com.cecer1.hypixelutils.events.adapters;

import com.cecer1.hypixelutils.events.EventAdapter;
import com.cecer1.hypixelutils.events.EventManager;
import com.cecer1.hypixelutils.events.eventdata.OnRenderEventData;
import net.minecraft.client.Minecraft;

public class LiteLoaderOnRenderAdapter extends EventAdapter {
    public LiteLoaderOnRenderAdapter(EventManager manager) {
        super(manager);
    }

    public void trigger(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
        OnRenderEventData eventData = new OnRenderEventData(inGame);
        manager.fireEvent(eventData);
    }
}
