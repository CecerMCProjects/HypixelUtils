package com.cecer1.hypixelutils.events;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;

public class FilterGuildChatProcessor implements IOnChatEventHandler
{
    @Override
    public void onChat(IOnChatEventData event) {

        if(!HypixelUtilsCore.config.isFilterGuildChatEnabled())
            return;

        if (ChatUtilities.compareChatComponent(ChatUtilities.getRootChatComponent(event.getMessage()), "{\"color\":\"dark_green\",\"text\":\"Guild \\u003e \"}"))
            event.setCanceled(true);
    }
}