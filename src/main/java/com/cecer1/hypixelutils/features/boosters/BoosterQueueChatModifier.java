package com.cecer1.hypixelutils.features.boosters;

import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.utils.ChatUtilities;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class BoosterQueueChatModifier implements IOnChatEventHandler
{
    public BoosterQueue latestQueue;
    private BoosterQueue _queue;
    
    
    public BoosterQueueChatModifier() {
        latestQueue = null;
        _queue = new BoosterQueue();
    }
    
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
    }
    
    @Override
    public void onEvent(OnChatEventData data) {
        if(!_queue.tryParseChat(data.getMessage())) {
            return;
        }
        data.setCanceled(true);

        if(_queue.state == BoosterQueue.BoosterQueueParseProgress.Done) {
            latestQueue = _queue;
            _queue.printToChat();
            _queue = new BoosterQueue();
        }
    }
    
    private IChatComponent[] _hoverEventChatComponents = new IChatComponent[] {
            ChatUtilities.QuickFormatting.yellow(new ChatComponentText("Click to ")),
            ChatUtilities.QuickFormatting.green(new ChatComponentText("tip")),
            ChatUtilities.QuickFormatting.yellow(new ChatComponentText(" and ")),
            ChatUtilities.QuickFormatting.aqua(new ChatComponentText("thank ")),
            ChatUtilities.QuickFormatting.yellow(new ChatComponentText(" for their booster!"))
    };
}