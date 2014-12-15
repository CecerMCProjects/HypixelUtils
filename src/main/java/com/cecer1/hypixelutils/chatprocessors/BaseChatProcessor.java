package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.HypixelUtils;
import com.cecer1.hypixelutils.UtilityMethods;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.config.Property;

public abstract class BaseChatProcessor
{
	private Property _configProperty;
	private boolean _enabled;

	public boolean isEnabled()
	{
		return _enabled;
	}
	public BaseChatProcessor setEnabled(boolean enabled)
	{
		_enabled = enabled;
        _configProperty.set(_enabled);
        HypixelUtils.syncConfig();
		return this;
	}

    public BaseChatProcessor(Property configProperty, boolean enabledByDefault)
    {
        _configProperty = configProperty;
        _enabled = _configProperty.getBoolean(enabledByDefault);
    }
	
	@SubscribeEvent
    public void internalOnChat(ClientChatReceivedEvent event)
    {
		if(_enabled && UtilityMethods.isCurrentServerHypixel())
			onChat(event);
	}
	
	public abstract void onChat(ClientChatReceivedEvent event);
}
