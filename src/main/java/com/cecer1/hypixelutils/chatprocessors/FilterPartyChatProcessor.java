package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.UtilityMethods;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;

public class FilterPartyChatProcessor extends BaseChatProcessor
{
    public FilterPartyChatProcessor(Property property, boolean enabledByDefault) {
        super(property, enabledByDefault);
    }

    @Override
    public void onChat(ClientChatReceivedEvent event)
    {
        if (UtilityMethods.compareChatComponent(UtilityMethods.getRootChatComponent(event.message), "{\"color\":\"blue\",\"text\":\"Party \\u003e \"}"))
            event.setCanceled(true);
    }
}
