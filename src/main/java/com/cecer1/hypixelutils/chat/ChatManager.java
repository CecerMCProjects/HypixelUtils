package com.cecer1.hypixelutils.chat;

import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatManager implements IOnChatEventHandler {

    private Set<IChatMessageSubscriber> _subscribers;
    public ChatManager() {
        _subscribers = new HashSet<IChatMessageSubscriber>();
    }

    public void subscribe(IChatMessageSubscriber subscriber) {
        _subscribers.add(subscriber);
    }
    public void unsubscribe(IChatMessageSubscriber subscriber) {
        _subscribers.remove(subscriber);
    }

    @Override
    public void onChat(IOnChatEventData event) {
        try {
            ChatMessage message = tryParseChatComponent(event.getMessage());
            if(message != null) {
                for(IChatMessageSubscriber subscriber : _subscribers) {
                    if(!subscriber.processChatMessage(message)) {
                        event.setCanceled(true);
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public ChatMessage tryParseChatComponent(IChatComponent message) {
        List<IChatComponent> components = ChatUtilities.getFlattenedChatComponent(message, true);

        EnumSet<ChatType> chatTypes = EnumSet.noneOf(ChatType.class);
        IChatComponent tag;
        String name;
        String text;

        boolean matchFailed = false;
        while(!matchFailed && components.size() > 0) {
            IChatComponent component = components.get(0);

            if(isMsgFromChatComponent(component)) {
                chatTypes.add(ChatType.MSG_FROM);
                components.remove(0);
                continue;
            }

            if(isMsgToChatComponent(component)) {
                chatTypes.add(ChatType.MSG_TO);
                components.remove(0);
                continue;
            }

            if(isPartyChatComponent(component)) {
                chatTypes.add(ChatType.PARTY);
                components.remove(0);
                continue;
            }

            if(isGuildChatComponent(component)) {
                chatTypes.add(ChatType.GUILD);
                components.remove(0);
                continue;
            }

            if(isShoutChatComponent(component)) {
                chatTypes.add(ChatType.SHOUT);
                components.remove(0);
                continue;
            }

            if(isTeamCopsChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_COPS);
                components.remove(0);
                continue;
            }

            if(isTeamCrimsChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_CRIMS);
                components.remove(0);
                continue;
            }

            if(isTeamUhcChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_UHC);
                components.remove(0);
                continue;
            }

            if(isTeamSpectatorChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_SPECTATOR);
                components.remove(0);
                continue;
            }

            if(isTeamRedChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_RED);
                components.remove(0);
                continue;
            }

            if(isTeamGreenChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_GREEN);
                components.remove(0);
                continue;
            }

            if(isTeamBlueChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_BLUE);
                components.remove(0);
                continue;
            }

            if(isTeamYellowChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_YELLOW);
                components.remove(0);
                continue;
            }

            if(isTeamVampiresChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_VAMPIRES);
                components.remove(0);
                continue;
            }

            if(isTeamSurvivorsChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_SURVIVORS);
                components.remove(0);
                continue;
            }

            if(isTeamHuntersChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_HUNTERS);
                components.remove(0);
                continue;
            }

            if(isTeamAnimalsChatComponent(component)) {
                chatTypes.add(ChatType.TEAM_ANIMALS);
                components.remove(0);
                continue;
            }

            matchFailed = true;
        }

        ChatMessage chatMessage = tryParsePureChatMessage(components);

        if(chatMessage != null) {
            chatMessage.chatTypes.addAll(chatTypes);
        }

        return chatMessage;
    }

    private ChatMessage tryParsePureChatMessage(List<IChatComponent> remainingComponents) {
        if(remainingComponents.size() == 0)
            return null;

        IChatComponent tagComponent = null;
        String name = remainingComponents.get(0).getUnformattedText();
        String message = null;
        IChatComponent lastComponent = remainingComponents.get(remainingComponents.size() - 1);

        if(name.length() == 0)
            return null;

        if(name.charAt(0) == '[') { // Tag detected!
            int closingIndex = name.indexOf("] ");
            if(closingIndex != -1) { // Includes closing square bracket. (Single component tag). This contains the name as well so we need to split it up.
                String tagString;
                if(closingIndex+2 >= name.length())
                    tagString = name;
                else
                    tagString = name.substring(0, closingIndex+1); // Strip out the tag from the name

                tagComponent = new ChatComponentText(tagString).setChatStyle(remainingComponents.get(0).getChatStyle()); // Create a chat component containing only the tag.
                name = name.substring(closingIndex+2);
                message = lastComponent.getUnformattedText();
            } else { // Tag is split over multiple components. Add them all till we find their name.
                tagComponent = remainingComponents.get(0);

                for (int i = 1; i < remainingComponents.size(); i++) {
                    IChatComponent currentComponent = remainingComponents.get(i);

                    String componentText = currentComponent.getUnformattedText();
                    closingIndex = componentText.indexOf("] ");
                    if (closingIndex != -1) { // Includes closing square bracket. This contains the name as well so we need to split it up.
                        String tagString;
                        if(closingIndex+2 >= name.length())
                            tagString = name;
                        else
                            tagString = name.substring(0, closingIndex+1); // Strip out the tag from the name

                        tagComponent = new ChatComponentText(tagString).setChatStyle(remainingComponents.get(0).getChatStyle()); // Create a chat component containing only the tag.
                        name = name.substring(closingIndex+2);
                        message = lastComponent.getUnformattedText();
                    }
                }
            }
        } else {
            if(remainingComponents.size() != 2)
                return null;

            IChatComponent nameComponent = remainingComponents.get(0);
            if(nameComponent.getChatStyle().getColor() != EnumChatFormatting.GRAY)
                return null;

            name = nameComponent.getUnformattedText();
            message = remainingComponents.get(1).getUnformattedText();
        }

        boolean colonFound = false;
        if(name.endsWith(": ")) { // Remove the ": " from the end of the name if it is there (such as with private messages)
            name = name.substring(0, name.length() - 2).trim(); // Trim off spaces round name
            colonFound = true;
        } else if(message.startsWith(": ")) { // If not then remove it from the start of message if it is there (such as most other chat)
             message = message.substring(2).trim(); // Trim off spaces round message
            colonFound = true;
        }

        if(!colonFound)
            return null;

        if(tagComponent != null)
            return new ChatMessage(new PlayerName(tagComponent, name), lastComponent.getChatStyle(), message);
        return new ChatMessage(new PlayerName(name), lastComponent.getChatStyle(), message);
    }

    private static final IChatComponent partyChatComponent = new ChatComponentText("Party \u003e ").setChatStyle(ChatUtilities.ChatPresets.BLUE);
    public boolean isPartyChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, partyChatComponent);
    }

    private static final IChatComponent guildChatComponent = new ChatComponentText("Guild \u003e ").setChatStyle(ChatUtilities.ChatPresets.GREEN);
    public boolean isGuildChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, guildChatComponent);
    }

    private static final IChatComponent shoutChatComponent = new ChatComponentText("[SHOUT] ").setChatStyle(ChatUtilities.ChatPresets.GOLD);
    public boolean isShoutChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, shoutChatComponent);
    }

    private static final IChatComponent teamCopsChatComponent = new ChatComponentText("[銐]").setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA);
    public boolean isTeamCopsChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamCopsChatComponent);
    }

    private static final IChatComponent teamCrimsChatComponent = new ChatComponentText("[銑]").setChatStyle(ChatUtilities.ChatPresets.DARK_RED);
    public boolean isTeamCrimsChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamCrimsChatComponent);
    }

    private static final IChatComponent teamUhcChatComponent = new ChatComponentText("[TEAM] ").setChatStyle(ChatUtilities.ChatPresets.GREEN);
    public boolean isTeamUhcChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamUhcChatComponent);
    }

    private static final IChatComponent teamSpectatorChatComponent = new ChatComponentText("[SPECTATOR] ").setChatStyle(ChatUtilities.ChatPresets.GRAY);
    public boolean isTeamSpectatorChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamSpectatorChatComponent);
    }

    private static final IChatComponent teamRedChatComponent = new ChatComponentText("[RED] ").setChatStyle(ChatUtilities.ChatPresets.RED);
    public boolean isTeamRedChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamRedChatComponent);
    }

    private static final IChatComponent teamGreenChatComponent = new ChatComponentText("[GREEN] ").setChatStyle(ChatUtilities.ChatPresets.GREEN);
    public boolean isTeamGreenChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamGreenChatComponent);
    }

    private static final IChatComponent teamBlueChatComponent = new ChatComponentText("[BLUE] ").setChatStyle(ChatUtilities.ChatPresets.BLUE);
    public boolean isTeamBlueChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamBlueChatComponent);
    }

    private static final IChatComponent teamYellowChatComponent = new ChatComponentText("[YELLOW] ").setChatStyle(ChatUtilities.ChatPresets.YELLOW);
    public boolean isTeamYellowChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamYellowChatComponent);
    }

    private static final IChatComponent teamVampiresChatComponent = new ChatComponentText("[V] ").setChatStyle(ChatUtilities.ChatPresets.RED);
    public boolean isTeamVampiresChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamVampiresChatComponent);
    }

    private static final IChatComponent teamSurvivorsChatComponent = new ChatComponentText("[S] ").setChatStyle(ChatUtilities.ChatPresets.BLUE);
    public boolean isTeamSurvivorsChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamSurvivorsChatComponent);
    }

    private static final IChatComponent teamHuntersChatComponent = new ChatComponentText("[H]").setChatStyle(ChatUtilities.ChatPresets.GOLD);
    public boolean isTeamHuntersChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamHuntersChatComponent);
    }

    private static final IChatComponent teamAnimalsChatComponent = new ChatComponentText("[A]").setChatStyle(ChatUtilities.ChatPresets.AQUA);
    public boolean isTeamAnimalsChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamAnimalsChatComponent);
    }

    private static final IChatComponent msgFromChatComponent = new ChatComponentText("From ").setChatStyle(ChatUtilities.ChatPresets.LIGHT_PURPLE);
    public boolean isMsgFromChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, msgFromChatComponent);
    }

    private static final IChatComponent msgToChatComponent = new ChatComponentText("To ").setChatStyle(ChatUtilities.ChatPresets.LIGHT_PURPLE);
    public boolean isMsgToChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, msgToChatComponent);
    }
}
