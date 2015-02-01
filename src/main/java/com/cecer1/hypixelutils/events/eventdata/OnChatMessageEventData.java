package com.cecer1.hypixelutils.events.eventdata;

import com.cecer1.hypixelutils.chat.ChatType;
import com.cecer1.hypixelutils.chat.PlayerName;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.EnumSet;

public class OnChatMessageEventData implements IEventData {
    private final PlayerName _playerName;
    private final IChatComponent _message;
    public EnumSet<ChatType> chatTypes;
    private boolean _canceled;

    public OnChatMessageEventData(PlayerName playerName, ChatStyle messageStyle, String message) {
        _playerName = playerName;
        _message = new ChatComponentText(message).setChatStyle(messageStyle);
        chatTypes = EnumSet.noneOf(ChatType.class);
    }

    public PlayerName getPlayerName() {
        return _playerName;
    }

    public IChatComponent getMessage() {
        return _message;
    }

    @Override
    public boolean isCanceled() {
        return _canceled;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        _canceled = cancel;
        return this;
    }
}
