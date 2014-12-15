package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.AbstractedSrgMethods;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;

public class DebugChatProcessor extends BaseChatProcessor
{
    public DebugChatProcessor()
    {
        super();
    }

    public DebugChatProcessor(Property configProperty, boolean enabledByDefault)
    {
        super(configProperty, enabledByDefault);
    }

    @Override
    public void onChat(ClientChatReceivedEvent event)
    {
        System.out.println(AbstractedSrgMethods.convertChatComponentToJson(event.message));
    }
}
