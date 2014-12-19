package com.cecer1.hypixelutils.events;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.client.Minecraft;

public class BypassLobbyCommandProtectionProcessor implements IOnChatEventHandler
{
    private int _enableBypasses;

    private boolean isEnabled()
    {
        if(!UtilityMethods.isCurrentServerHypixel())
            return false;

        if(_enableBypasses > 0)
        {
            _enableBypasses--;
            return true;
        }
        return HypixelUtilsCore.config.isLobbyProtectionEnabled();
    }
    public BypassLobbyCommandProtectionProcessor enableOnce()
    {
        _enableBypasses++;
        return this;
    }

    @Override
    public void onChat(IOnChatEventData event) {
        if(!isEnabled())
            return;

        if (ChatUtilities.compareChatComponent(event.getMessage(), "{\"color\":\"green\",\"text\":\"Are you sure? Type /lobby again if you really want to quit.\"}"))
        {
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby");
            event.setCanceled(true);
        }
    }
}