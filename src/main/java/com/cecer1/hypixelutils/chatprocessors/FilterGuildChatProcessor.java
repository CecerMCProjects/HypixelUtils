package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.UtilityMethods;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class FilterGuildChatProcessor extends BaseChatProcessor
{
    @Override
    public void onChat(ClientChatReceivedEvent event)
    {
        if (UtilityMethods.compareChatComponent(UtilityMethods.getRootChatComponent(event.message), "{\"color\":\"dark_green\",\"text\":\"Guild \\u003e \"}"))
            event.setCanceled(true);
    }
}
