package com.cecer1.hypixelutils.chatprocessors;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public abstract class BaseChatProcessor
{
	private boolean _enabled;
	
	public boolean isEnabled()
	{
		return _enabled;
	}
	public BaseChatProcessor setEnabled(boolean enabled)
	{
		_enabled = enabled;
		return this;
	}
	
	@SubscribeEvent
    public void internalChatHandled(ClientChatReceivedEvent event)
    {
		if(_enabled)
			onChat(event);
	}
	
	public abstract void onChat(ClientChatReceivedEvent event);
}
