package com.cecer1.modframework.forge.events.adapters;

import com.cecer1.modframework.common.events.EventAdapter;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.forge.events.ForgeOnChatEventData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ForgeOnChatAdapter extends EventAdapter {
    public ForgeOnChatAdapter(EventManager manager) {
        super(manager);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        ForgeOnChatEventData eventData = new ForgeOnChatEventData(event);
        manager.fireEvent(eventData);
    }
}
