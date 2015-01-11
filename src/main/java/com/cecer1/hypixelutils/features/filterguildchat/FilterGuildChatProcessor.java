package com.cecer1.hypixelutils.features.filterguildchat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatMessage;
import com.cecer1.hypixelutils.chat.ChatType;
import com.cecer1.hypixelutils.chat.IChatMessageSubscriber;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;

public class FilterGuildChatProcessor implements IChatMessageSubscriber
{
    @Override
    public boolean processChatMessage(ChatMessage message) {
        if(!HypixelUtilsCore.config.isFilterGuildChatEnabled())
            return true;
        return !message.chatTypes.contains(ChatType.GUILD);
    }
}