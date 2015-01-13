package com.cecer1.hypixelutils.features.boosters;

import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;
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
    public void onChat(IOnChatEventData event) {
        if(!_queue.tryParseChat(event.getMessage())) {
            return;
        }
        event.setCanceled(true);
        
        if(_queue.state == BoosterQueue.BoosterQueueParseProgress.Done) {
            latestQueue = _queue;
            _queue.printToChat();
            _queue = new BoosterQueue();
        }
    }

    private IChatComponent[] _hoverEventChatComponents = new IChatComponent[] {
            new ChatComponentText("Click to ").setChatStyle(ChatUtilities.ChatPresets.YELLOW),
            new ChatComponentText("tip").setChatStyle(ChatUtilities.ChatPresets.GREEN),
            new ChatComponentText(" and ").setChatStyle(ChatUtilities.ChatPresets.YELLOW),
            new ChatComponentText("thank ").setChatStyle(ChatUtilities.ChatPresets.AQUA),
            new ChatComponentText(" for their booster!").setChatStyle(ChatUtilities.ChatPresets.YELLOW)
    };
}