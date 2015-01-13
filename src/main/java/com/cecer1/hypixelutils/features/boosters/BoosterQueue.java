package com.cecer1.hypixelutils.features.boosters;

import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoosterQueue {
    public BoosterQueueParseProgress state = BoosterQueueParseProgress.None;
    
    private Map<String,BoosterQueueEntry> _entries = new HashMap<String, BoosterQueueEntry>();
    private static final Pattern _pattern = Pattern.compile("^(\\S+) of Triple Coins from (.*)$");
    
    public boolean tryParseChat(IChatComponent component) {
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
        if(variableComponent.getChatStyle().getColor() != EnumChatFormatting.GREEN)
            return false;

        Matcher m = _pattern.matcher(variableComponent.getUnformattedText());
        if(!m.matches()) {
            if(variableComponent.getUnformattedText().equals("No Triple Coins Boosters queued")) {
                _entries.put(rootComponent.getUnformattedText(), new BoosterQueueEntry("", new String[0]));
                return true;
            }
            return false;
        }
        
        String[] names = m.group(2).split(", ");
        _entries.put(rootComponent.getUnformattedText(), new BoosterQueueEntry(m.group(1), names));
        return true;
    }
    
    private static final IChatComponent[] staticOutputComponents = new IChatComponent[] {
            new ChatComponentText("-----------------------------------------------------").setChatStyle(ChatUtilities.ChatPresets.WHITE),
            new ChatComponentText("                          ").setChatStyle(ChatUtilities.ChatPresets.WHITE).appendSibling(new ChatComponentText("Network Booster Queue").setChatStyle(ChatUtilities.ChatPresets.AQUA)),
            new ChatComponentText("").setChatStyle(ChatUtilities.ChatPresets.WHITE),
            new ChatComponentText(", ").setChatStyle(ChatUtilities.ChatPresets.GRAY),
            new ChatComponentText(" and ").setChatStyle(ChatUtilities.ChatPresets.GRAY)
    };
    private static IChatComponent getBoosterQueueLineComponent(String game, String time, String[] names) {
        if(names.length == 0) {
            return new ChatComponentText(game).setChatStyle(ChatUtilities.ChatPresets.RED)
                    .appendSibling(new ChatComponentText(": No Network Boosters queued.").setChatStyle(ChatUtilities.ChatPresets.GRAY));
        }
        
        IChatComponent component = new ChatComponentText(game + " (" + time + ")").setChatStyle(ChatUtilities.ChatPresets.GREEN.createShallowCopy().setParentStyle(TipAndThankChatModifier.getClickableTipAndThankChatStyle(names[0])));
        component.appendSibling(new ChatComponentText(": ").setChatStyle(ChatUtilities.ChatPresets.GRAY));
        component.appendSibling(new ChatComponentText(names[0]).setChatStyle(ChatUtilities.ChatPresets.GOLD));
        
        if(names.length > 1) {
            if(names.length <= 24) {
                for (int i = 1; i < names.length - 1; i++) {
                    component.appendSibling(staticOutputComponents[3]);
                    component.appendSibling(new ChatComponentText(names[i]).setChatStyle(ChatUtilities.ChatPresets.WHITE));
                }
                component.appendSibling(staticOutputComponents[4]);
                component.appendSibling(new ChatComponentText(names[names.length - 1]).setChatStyle(ChatUtilities.ChatPresets.WHITE));
            } else {
                for (int i = 1; i < 24; i++) {
                    component.appendSibling(staticOutputComponents[3]);
                    component.appendSibling(new ChatComponentText(names[i]).setChatStyle(ChatUtilities.ChatPresets.WHITE));
                }
                component.appendSibling(new ChatComponentText(" and ").setChatStyle(ChatUtilities.ChatPresets.GRAY));
                component.appendSibling(new ChatComponentText((names.length-24) + " others").setChatStyle(ChatUtilities.ChatPresets.WHITE));
                component.appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.GRAY));
            }
        }
        return component;
    }
    public void printToChat() {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        player.addChatComponentMessage(staticOutputComponents[0]);
        player.addChatComponentMessage(staticOutputComponents[1]);
        player.addChatComponentMessage(staticOutputComponents[2]);
        
        for(String game : _entries.keySet()) {
            BoosterQueueEntry entry = _entries.get(game);
            player.addChatComponentMessage(getBoosterQueueLineComponent(game, entry.time, entry.names));
        }
        player.addChatComponentMessage(staticOutputComponents[0]);
    }

    private class BoosterQueueEntry {
        public final String time;
        public final String[] names;
        
        public BoosterQueueEntry(String time, String[] names) {
            this.time = time;
            this.names = names;
        }
    }

    public enum BoosterQueueParseProgress {
        HeaderBar, HeaderTitle, HeaderSpacer, FooterBar, Done, None

    }
}
