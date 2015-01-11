package com.cecer1.hypixelutils.commands;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatMessage;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.util.IChatComponent;

public class HypixelCommandJobWhereami extends HypixelCommandJob {
    private boolean _started = false;
    private boolean _done = false;
    private String _result = null;
    private IHypixelCommandCallbackWhereami _callback;

    public HypixelCommandJobWhereami(IHypixelCommandCallbackWhereami callback) {
        _callback = callback;
    }

    @Override
    public boolean processChatMessage(ChatMessage message) {
        return true;
    }

    @Override
    public void onChat(IOnChatEventData event) {
        IChatComponent message = event.getMessage();
        if (ChatUtilities.compareChatComponent(ChatUtilities.getRootChatComponent(message), "{\"color\":\"aqua\",\"text\":\"You are currently on server \"}")) {
            IChatComponent locationComponent = (IChatComponent) message.getSiblings().get(0);
            _result = locationComponent.getUnformattedText();
            _done = true;
            event.setCanceled(true);
        } else if (ChatUtilities.compareChatComponent(message, "{\"color\":\"red\",\"text\":\"An internal error occurred whilst executing this command, please check the console log for details.\"}")) {
            _result = "limbo";
            _done = true;
            event.setCanceled(true);
        }
    }

    @Override
    public void trigger() {
        HypixelUtilsCore.sendChatMessage("/whereami");
        _started = true;
    }


    @Override
    public boolean isStarted() {
        return _started;
    }
    @Override
    public boolean isDone() {
        return _done;
    }

    @Override
    public void complete() {
        _callback.result(_result);
    }

    @Override
    public Cooldown getCooldown() {
        return null;
    }
}
