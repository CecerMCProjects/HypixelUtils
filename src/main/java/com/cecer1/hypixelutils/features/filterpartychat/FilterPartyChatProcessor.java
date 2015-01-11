package com.cecer1.hypixelutils.features.filterpartychat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatMessage;
import com.cecer1.hypixelutils.chat.ChatType;
import com.cecer1.hypixelutils.chat.IChatMessageSubscriber;

public class FilterPartyChatProcessor implements IChatMessageSubscriber
{
    @Override
    public boolean processChatMessage(ChatMessage message) {
        if(!HypixelUtilsCore.config.isFilterPartyChatEnabled())
            return true;
        return !message.chatTypes.contains(ChatType.PARTY);
    }
}