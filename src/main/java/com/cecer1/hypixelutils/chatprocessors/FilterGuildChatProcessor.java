package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.Utility;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;

public class FilterGuildChatProcessor extends BaseChatProcessor
{
    public FilterGuildChatProcessor()
    {
        super();
    }

    public FilterGuildChatProcessor(Property configProperty, boolean enabledByDefault)
    {
        super(configProperty, enabledByDefault);
    }

    @Override
    public void onChat(ClientChatReceivedEvent event)
    {
        if (Utility.compareChatComponent(Utility.getRootChatComponent(event.message), "{\"color\":\"dark_green\",\"text\":\"Guild \\u003e \"}"))
            event.setCanceled(true);
    }
}
