package com.cecer1.hypixelutils.features.clickto;

import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.utils.ChatUtilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClickToAcceptGuildInviteChatProcessor implements IOnChatEventHandler
{
    private static Pattern _guildInvitePattern = Pattern.compile("^(?:\\[.+\\] |)[a-zA-Z0-9_]{1,16} has invited you to join their guild, .+! Use /guild accept ([a-zA-Z0-9_]{1,16}) to join!$");

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
    }


    @Override
    public void onEvent(OnChatEventData data) {
        Matcher matcher = _guildInvitePattern.matcher(data.getMessage().getUnformattedText());
        if(!matcher.matches())
            return;

        String name = matcher.group(1);
        ChatUtilities.printRawChatComponent(data.getMessage());
        ChatOutputs.printClickToAcceptGuildInvite(name);
        data.setCanceled(true);
    }
}