package com.cecer1.hypixelutils.features.filterguildchat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatMessage;
import com.cecer1.hypixelutils.chat.ChatType;
import com.cecer1.hypixelutils.chat.IChatMessageSubscriber;

public class FilterGuildChatProcessor implements IChatMessageSubscriber
{
    @Override
    public boolean processChatMessage(ChatMessage message) {
        if(!HypixelUtilsCore.config.isFilterGuildChatEnabled())
            return true;
        return !message.chatTypes.contains(ChatType.GUILD);
    }
}