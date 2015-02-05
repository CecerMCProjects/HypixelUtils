package com.cecer1.hypixelutils.features.boosters;

import com.cecer1.hypixelutils.utils.ChatUtilities;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cecer1.hypixelutils.utils.ChatUtilities.QuickFormatting.*;

public class BoosterQueue {
    public BoosterQueueParseProgress state = BoosterQueueParseProgress.None;
    
    private Map<String,BoosterQueueEntry> _entries = new HashMap<String, BoosterQueueEntry>();
    private static final Pattern _startPattern = Pattern.compile("^Triple Coins from (.*)$");
    private static final Pattern _endPattern = Pattern.compile("^([A-Za-z_]{1,16}) and (\\d+) more$");
    
    public boolean  tryParseChat(IChatComponent component) {
        if(state == BoosterQueueParseProgress.Done) {
            return false;
        }
        
        if(state == BoosterQueueParseProgress.None) {
            if(ChatUtilities.compareChatComponent(component, "{\"color\":\"white\",\"text\":\"-----------------------------------------------------\"}")) {
                state = BoosterQueueParseProgress.HeaderBar;
                return true;
            }
            return false;
        }

        if(state == BoosterQueueParseProgress.HeaderBar) {
            if(ChatUtilities.compareChatComponent(component, "{\"color\":\"white\",\"extra\":[{\"color\":\"aqua\",\"text\":\"Network Booster Queue\"}],\"text\":\"                          \"}")) {
                state = BoosterQueueParseProgress.HeaderTitle;
                return true;
            }
            return false;
        }

        if(state == BoosterQueueParseProgress.HeaderTitle) {
            if(ChatUtilities.compareChatComponent(component, "{\"color\":\"white\",\"text\":\"\"}")) {
                state = BoosterQueueParseProgress.HeaderSpacer;
                return true;
            }
            return false;
        }

        if(state == BoosterQueueParseProgress.HeaderSpacer) {
            if(ChatUtilities.compareChatComponent(component, "{\"color\":\"white\",\"text\":\"-----------------------------------------------------\"}")) {
                state = BoosterQueueParseProgress.Done;
                return true;
            }
        }
        
        IChatComponent rootComponent = ChatUtilities.getRootChatComponent(component);
        if(rootComponent.getChatStyle().getColor() != EnumChatFormatting.AQUA)
            return false;
        
        List siblings = component.getSiblings();
        if(siblings.size() != 2)
            return false;
        
        if(!ChatUtilities.compareChatComponent((IChatComponent) siblings.get(0), "{\"color\":\"yellow\",\"text\":\" - \"}"))
            return false;
        
        IChatComponent variableComponent = (IChatComponent) siblings.get(1);
        Matcher m = _startPattern.matcher(variableComponent.getUnformattedText());
        if(variableComponent.getChatStyle().getColor() == EnumChatFormatting.RED) {
            if(variableComponent.getUnformattedText().equals("No Triple Coins Boosters queued")) {
                _entries.put(rootComponent.getUnformattedText(), new BoosterQueueEntry(new String[0], 0));
                return true;
            }
            return false;
        } else if(variableComponent.getChatStyle().getColor() == EnumChatFormatting.GREEN && m.matches()) {
            String[] names = m.group(1).split(", ");
            String lastName = names[names.length - 1];

            m = _endPattern.matcher(lastName); // If it contains " and ### more"
            int more = 0;
            if (m.matches()) { // Then remove it and store it elsewhere.
                more = Integer.parseInt(m.group(2));
                names[names.length - 1] = m.group(1);
            }
            _entries.put(rootComponent.getUnformattedText(), new BoosterQueueEntry(names, more));
            return true;
        }
        return false;
    }
    private static final IChatComponent[] staticOutputComponents = new IChatComponent[]{
            white(new ChatComponentText("-----------------------------------------------------")),
            aqua(new ChatComponentText("                          Network Booster Queue")),
            white(new ChatComponentText("")),
            gray(new ChatComponentText(", ")),
            gray(new ChatComponentText(" and ")),
            gray(new ChatComponentText(".")),
            new ChatComponentText("                        ")
    };
    static {
        IChatComponent buttonChatComponent = white(new ChatComponentText(""));
        buttonChatComponent
                .getChatStyle()
                .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tipandthank all"))
                .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ChatComponentText("")
                                .appendSibling(yellow(new ChatComponentText("Click to ")))
                                .appendSibling(green(new ChatComponentText("tip")))
                                .appendSibling(yellow(new ChatComponentText(" and ")))
                                .appendSibling(aqua(new ChatComponentText("thank ")))
                                .appendSibling(gold(new ChatComponentText("EVERYONE")))
                                .appendSibling(yellow(new ChatComponentText(" for their booster!")))));
        buttonChatComponent.appendSibling(new ChatComponentText("["));
        buttonChatComponent.appendSibling(gold(new ChatComponentText("Tip and Thank Everyone")));
        buttonChatComponent.appendSibling(new ChatComponentText("]"));
        staticOutputComponents[6].appendSibling(buttonChatComponent);
    }
    
    private static IChatComponent getBoosterQueueLineComponent(String game, String[] names, int more) {
        if(names.length == 0) {
            return new ChatComponentText("")
                    .appendSibling(red(new ChatComponentText(game)))
                    .appendSibling(gray(new ChatComponentText(": No Network Boosters queued.")));
        }
        
        IChatComponent component = new ChatComponentText("")
                .setChatStyle(TipAndThankChatModifier.getClickableTipAndThankChatStyle(names[0]))
                .appendSibling(green(new ChatComponentText(game)))
                .appendSibling(gray(new ChatComponentText(": ")))
                .appendSibling(gold(new ChatComponentText(names[0])));
        
        
        if(names.length > 1) {
            if(more == 0) {
                for (int i = 1; i < names.length - 1; i++) {
                    component.appendSibling(staticOutputComponents[3]);
                    component.appendSibling(white(new ChatComponentText(names[i])));
                }
                component.appendSibling(staticOutputComponents[4]);
                component.appendSibling(white(new ChatComponentText(names[names.length - 1])));
            } else {
                for (int i = 1; i < names.length; i++) {
                    component.appendSibling(staticOutputComponents[3]);
                    component.appendSibling(white(new ChatComponentText(names[i])));
                }
                component.appendSibling(staticOutputComponents[4]);
                component.appendSibling(white(new ChatComponentText(more + " others")));
                component.appendSibling(staticOutputComponents[5]);
            }
        }
        return component;
    }
    public void printToChat() {
        ChatUtilities.printRawChatComponent(staticOutputComponents[0]); // "-----------------------------------------------------"
        ChatUtilities.printRawChatComponent(staticOutputComponents[1]); // "                          Network Booster Queue"
        ChatUtilities.printRawChatComponent(staticOutputComponents[2]); // ""
        
        for(String game : _entries.keySet()) {
            BoosterQueueEntry entry = _entries.get(game);
            ChatUtilities.printRawChatComponent(getBoosterQueueLineComponent(game, entry.names, entry.more));
        }
        //ChatUtilities.printRawChatComponent(staticOutputComponents[6]); // "                        [Tip and Thank Everyone]"
        ChatUtilities.printRawChatComponent(staticOutputComponents[0]); // "-----------------------------------------------------"
    }
    
    public String[] getActiveBoosterPlayerName() {
        Set<String> playerNames = new HashSet<String>(_entries.size());
        for (BoosterQueueEntry entry : _entries.values()) {
            if(entry.names.length > 0) {
                playerNames.add(entry.names[0]);
            }
        }
        String[] returnArray = new String[playerNames.size()];
        return playerNames.toArray(returnArray);
    }

    private class BoosterQueueEntry {
        public final String[] names;
        public final int more;

        public BoosterQueueEntry(String[] names, int more) {
            this.names = names;
            this.more = more;
        }
    }

    public enum BoosterQueueParseProgress {
        HeaderBar, HeaderTitle, HeaderSpacer, FooterBar, Done, None
    }
}
