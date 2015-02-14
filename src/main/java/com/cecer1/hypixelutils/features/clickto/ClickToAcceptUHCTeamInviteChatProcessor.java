package com.cecer1.hypixelutils.features.clickto;

import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.utils.ChatUtilities;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.List;

public class ClickToAcceptUHCTeamInviteChatProcessor implements IOnChatEventHandler
{
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
    }
    @Override
    public void onEvent(OnChatEventData data) {
        IChatComponent rootComponent = ChatUtilities.getRootChatComponent(data.getMessage());

        if(rootComponent.getUnformattedText().length() != 0)
            return;

        List<IChatComponent> siblings = data.getMessage().getSiblings();
        if(!ChatUtilities.compareChatComponent(siblings.get(1), "{\"color\":\"green\",\"text\":\" has requested to team with you! Accept with \"}"))
            return;

        IChatComponent nameComponent = siblings.get(0);
        if(nameComponent.getChatStyle().getColor() != EnumChatFormatting.YELLOW)
            return;


        String name = nameComponent.getUnformattedText();
        ChatUtilities.printRawChatComponent(data.getMessage());
        ChatOutputs.printClickToAcceptUHCTeamInvite(name);
        data.setCanceled(true);
    }
}