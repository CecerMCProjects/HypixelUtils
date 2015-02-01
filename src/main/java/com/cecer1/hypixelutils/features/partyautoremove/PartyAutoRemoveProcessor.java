package com.cecer1.hypixelutils.features.partyautoremove;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.events.handlers.IOnConfigUpdatedEventHandler;
import com.cecer1.hypixelutils.utils.ChatUtilities;
import net.minecraft.util.IChatComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartyAutoRemoveProcessor implements IOnChatEventHandler, IOnConfigUpdatedEventHandler {

    private static Pattern _errorMessagePattern = Pattern.compile("^Your party can't queue for (.*): (.*) because (?:|\\[.+\\] )(\\S+) isn't online\\!$");

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
        if(data instanceof OnConfigUpdatedEventData)
            onEvent((OnConfigUpdatedEventData)data);
    }

    @Override
    public void onEvent(OnChatEventData data) {
        if(!HypixelUtilsCore.configHelper.partyAutoRemoveOfflineEnabled.getValue(false))
            return;

        IChatComponent rootComponent = ChatUtilities.getRootChatComponent(data.getMessage());

        Matcher matcher = _errorMessagePattern.matcher(data.getMessage().getUnformattedText());
        if(matcher.matches())
        {
            data.setCanceled(true);
            String offlinePlayerName = matcher.group(3);

            HypixelUtilsCore.sendChatMessage("/party remove " + offlinePlayerName);
            ChatOutputs.printPartyAutoRemovedNotice(offlinePlayerName);
        }
    }


    @Override
    public void onEvent(OnConfigUpdatedEventData data) {
        String key = "party.removeoffline.enabled";
        if(data.isUpdatedKey(key)) {
            ChatOutputs.printPartyAutoRemoveEnabledStatus(HypixelUtilsCore.configHelper.partyAutoRemoveOfflineEnabled.getValue(false));
        }
    }
}
