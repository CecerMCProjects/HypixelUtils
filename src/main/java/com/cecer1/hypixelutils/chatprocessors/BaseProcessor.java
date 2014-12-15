package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.HypixelUtils;
import com.cecer1.hypixelutils.Utility;
import net.minecraftforge.common.config.Property;

public abstract class BaseProcessor
{
	private Property _configProperty;
    private boolean _enabled;
    private int _enableBypasses;

	public boolean isEnabled()
	{
		return _enabled;
	}
    public BaseProcessor enableOnce()
    {
        _enableBypasses++;
        return this;
    }

    public BaseProcessor setEnabled(boolean enabled)
    {
        _enabled = enabled;
        if(_configProperty != null)
            _configProperty.set(_enabled);
        HypixelUtils.syncConfig();
        return this;
    }

    public boolean isEnabledAtAll()
    {
        if(!Utility.isCurrentServerHypixel())
            return false;

        if(_enableBypasses > 0)
        {
            _enableBypasses--;
            return true;
        }
        return _enabled;
    }

    public BaseProcessor()
    {
        _enabled = true;
    }

    public BaseProcessor(Property configProperty, boolean enabledByDefault)
    {
        _configProperty = configProperty;
        _enabled = _configProperty.getBoolean(enabledByDefault);
    }
}
