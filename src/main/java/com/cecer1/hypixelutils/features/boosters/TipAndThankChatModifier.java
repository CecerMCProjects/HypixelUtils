package com.cecer1.hypixelutils.features.boosters;

import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.utils.ChatUtilities;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cecer1.hypixelutils.utils.ChatUtilities.QuickFormatting.*;

public class TipAndThankChatModifier implements IOnChatEventHandler
{
    private Pattern _startBoosterPattern = Pattern.compile("^([A-Za-z0-9_]{1,16})\u0027s triple coins is active for this game!$");
    private Pattern _endBoosterPattern = Pattern.compile("^Your game was boosted by ([A-Za-z0-9_]{1,16})\u0027s triple coins!$");

    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData)
            onEvent((OnChatEventData)data);
    }
    
    @Override
    public void onEvent(OnChatEventData data) {
        Matcher m = _startBoosterPattern.matcher(data.getMessage().getUnformattedText());
        if(m.matches()) {
            String name = m.group(1);
            data.setCanceled(true);
            ChatUtilities.printChatComponent(getClickableStartBoosterMessage(name));
            return;
        }
        
        m = _endBoosterPattern.matcher(data.getMessage().getUnformattedText());
        if(m.matches()) {
            String name = m.group(1);
            data.setCanceled(true);
            ChatUtilities.printChatComponent(getClickableEndBoosterMessage(name));
            return;
        }
    }

    private static IChatComponent[] _hoverEventChatComponents = new IChatComponent[] {
            yellow(new ChatComponentText("Click to ")),
            green(new ChatComponentText("tip")),
            yellow(new ChatComponentText(" and ")),
            aqua(new ChatComponentText("thank ")),
            yellow(new ChatComponentText(" for their booster!"))
    };
    
    public static ChatStyle getClickableTipAndThankChatStyle(String name) {
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tipandthank " + name);
        IChatComponent hoverChatComponent = new ChatComponentText("")
                .appendSibling(_hoverEventChatComponents[0])
                .appendSibling(_hoverEventChatComponents[1])
                .appendSibling(_hoverEventChatComponents[2])
                .appendSibling(_hoverEventChatComponents[3])
                .appendSibling(gold(new ChatComponentText(name)))
                .appendSibling(_hoverEventChatComponents[4]);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverChatComponent);
        
        return new ChatStyle().setChatClickEvent(clickEvent).setChatHoverEvent(hoverEvent);
    }
    
    private IChatComponent getClickableStartBoosterMessage(String name) {
        ChatStyle clickStyle = getClickableTipAndThankChatStyle(name);

        return new ChatComponentText("").setChatStyle(clickStyle)
                .appendSibling(gold(new ChatComponentText(name)))
                .appendSibling(darkAqua(new ChatComponentText("\u0027s ")))
                .appendSibling(red(bold(new ChatComponentText("triple coins"))))
                .appendSibling(darkAqua(new ChatComponentText(" is active for this game!")));
    }
    private IChatComponent getClickableEndBoosterMessage(String name) {
        ChatStyle clickStyle = getClickableTipAndThankChatStyle(name);

        return new ChatComponentText("").setChatStyle(clickStyle)
                .appendSibling(darkAqua(new ChatComponentText("Your game was boosted by ")))
                .appendSibling(gold(new ChatComponentText(name)))
                .appendSibling(darkAqua(new ChatComponentText("\u0027s ")))
                .appendSibling(red(new ChatComponentText("triple coins")))
                .appendSibling(darkAqua(new ChatComponentText("!")));
    }
}