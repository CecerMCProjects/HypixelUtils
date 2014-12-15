package com.cecer1.hypixelutils.chatprocessors;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;

public abstract class BaseChatProcessor extends BaseProcessor
{
    public BaseChatProcessor()
    {
        super();
    }

    public BaseChatProcessor(Property configProperty, boolean enabledByDefault)
    {
        super(configProperty, enabledByDefault);
    }

	@SubscribeEvent
    public void internalOnChat(ClientChatReceivedEvent event)
    {
		if(isEnabledAtAll())
			onChat(event);
	}
	
	public abstract void onChat(ClientChatReceivedEvent event);
}
