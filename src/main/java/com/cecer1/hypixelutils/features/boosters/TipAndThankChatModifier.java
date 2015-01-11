package com.cecer1.hypixelutils.features.boosters;

import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TipAndThankChatModifier implements IOnChatEventHandler
{
    private Pattern _startBoosterPattern = Pattern.compile("^([A-Za-z0-9_]{1,16})\u0027s triple coins is active for this game!$");
    private Pattern _endBoosterPattern = Pattern.compile("^Your game was boosted by ([A-Za-z0-9_]{1,16})\u0027s triple coins!$");

    @Override
    public void onChat(IOnChatEventData event) {
        Matcher m = _startBoosterPattern.matcher(event.getMessage().getUnformattedText());
        if(m.matches()) {
            String name = m.group(1);
            event.setCanceled(true);
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(getClickableStartBoosterMessage(name));
            return;
        }
        
        m = _endBoosterPattern.matcher(event.getMessage().getUnformattedText());
        if(m.matches()) {
            String name = m.group(1);
            event.setCanceled(true);
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(getClickableEndBoosterMessage(name));
            return;
        }
    }

    private IChatComponent[] _hoverEventChatComponents = new IChatComponent[] {
            new ChatComponentText("Click to ").setChatStyle(ChatUtilities.ChatPresets.YELLOW),
            new ChatComponentText("tip").setChatStyle(ChatUtilities.ChatPresets.GREEN),
            new ChatComponentText(" and ").setChatStyle(ChatUtilities.ChatPresets.YELLOW),
            new ChatComponentText("thank ").setChatStyle(ChatUtilities.ChatPresets.AQUA),
            new ChatComponentText(" for their booster!").setChatStyle(ChatUtilities.ChatPresets.YELLOW)
    };
    
    private IChatComponent getClickableStartBoosterMessage(String name) {
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tipandthank " + name);
        IChatComponent hoverChatComponent = new ChatComponentText("")
                .appendSibling(_hoverEventChatComponents[0])
                .appendSibling(_hoverEventChatComponents[1])
                .appendSibling(_hoverEventChatComponents[2])
                .appendSibling(_hoverEventChatComponents[3])
                .appendSibling(new ChatComponentText(name).setChatStyle(ChatUtilities.ChatPresets.GOLD))
                .appendSibling(_hoverEventChatComponents[4]);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverChatComponent);
        ChatStyle parentStyle = new ChatStyle().setChatClickEvent(clickEvent).setChatHoverEvent(hoverEvent);

        return new ChatComponentText("")
                .appendSibling(new ChatComponentText(name)).setChatStyle(ChatUtilities.ChatPresets.GOLD.createShallowCopy().setParentStyle(parentStyle))
                .appendSibling(new ChatComponentText("\u0027s ").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA.createShallowCopy().setParentStyle(parentStyle)))
                .appendSibling(new ChatComponentText("triple coins").setChatStyle(ChatUtilities.ChatPresets.RED.createShallowCopy().setParentStyle(parentStyle).setBold(true)))
                .appendSibling(new ChatComponentText(" is active for this game!").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA.createShallowCopy().setParentStyle(parentStyle)));
    }
    private IChatComponent getClickableEndBoosterMessage(String name) {
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tipandthank " + name);
        IChatComponent hoverChatComponent = new ChatComponentText("")
                .appendSibling(_hoverEventChatComponents[0])
                .appendSibling(_hoverEventChatComponents[1])
                .appendSibling(_hoverEventChatComponents[2])
                .appendSibling(_hoverEventChatComponents[3])
                .appendSibling(new ChatComponentText(name).setChatStyle(ChatUtilities.ChatPresets.GOLD))
                .appendSibling(_hoverEventChatComponents[4]);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverChatComponent);
        ChatStyle parentStyle = new ChatStyle().setChatClickEvent(clickEvent).setChatHoverEvent(hoverEvent);

        return new ChatComponentText("")
                .appendSibling(new ChatComponentText("Your game was boosted by ").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA.createShallowCopy().setParentStyle(parentStyle)))
                .appendSibling(new ChatComponentText(name)).setChatStyle(ChatUtilities.ChatPresets.GOLD.createShallowCopy().setParentStyle(parentStyle))
                .appendSibling(new ChatComponentText("\u0027s ").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA.createShallowCopy().setParentStyle(parentStyle)))
                .appendSibling(new ChatComponentText("triple coins").setChatStyle(ChatUtilities.ChatPresets.RED.createShallowCopy().setParentStyle(parentStyle).setBold(true)))
                .appendSibling(new ChatComponentText("!").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA.createShallowCopy().setParentStyle(parentStyle)));
    }
}