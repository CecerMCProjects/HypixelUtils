package com.cecer1.hypixelutils.chat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatEventData;
import com.cecer1.hypixelutils.events.eventdata.OnChatMessageEventData;
import com.cecer1.hypixelutils.events.handlers.IOnChatEventHandler;
import com.cecer1.hypixelutils.utils.ChatUtilities;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.EnumSet;
import java.util.List;

import static com.cecer1.hypixelutils.utils.ChatUtilities.QuickFormatting.*;

public class ChatManager implements IOnChatEventHandler {
    
    private boolean _guildMode = false; // Aqua
    private boolean _partyMode = false; // Gold
    private boolean _questMode = false; // Green
    private boolean _friendMode = false; // Blue
    private boolean _boosterMode = false; /// White
    // ??? // Yellow

    public boolean isGuildModeActive() {
        return _guildMode;
    }
    public boolean isPartyModeActive() {
        return _partyMode;
    }
    public boolean isQuestModeActive() {
        return _questMode;
    }
    public boolean isFriendModeActive() {
        return _friendMode;
    }
    public boolean isBoosterModeActive() {
        return _boosterMode;
    }


    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnChatEventData) {
            onEvent((OnChatEventData)data);
        }
    }
    
    @Override
    public void onEvent(OnChatEventData event) {
        try {
            OnChatMessageEventData message = tryParseChatComponent(event.getMessage());
            if(message != null) {
                HypixelUtilsCore.eventManager.fireEvent(message);
                if(message.isCanceled())
                    event.setCanceled(true);
            } else {
                if(event.getMessage().getUnformattedText().equals("-----------------------------------------------------")) {
                    if(event.getMessage().getChatStyle().getColor() == EnumChatFormatting.AQUA) {
                        _guildMode = !_guildMode;
                    }

                    if(event.getMessage().getChatStyle().getColor() == EnumChatFormatting.GOLD) {
                        _partyMode = !_partyMode;
                    }

                    if(event.getMessage().getChatStyle().getColor() == EnumChatFormatting.GREEN) {
                        _questMode = !_questMode;
                    }

                    if(event.getMessage().getChatStyle().getColor() == EnumChatFormatting.BLUE) {
                        _friendMode = !_friendMode;
                    }

                    if(event.getMessage().getChatStyle().getColor() == EnumChatFormatting.WHITE) {
                        _boosterMode = !_boosterMode;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public OnChatMessageEventData tryParseChatComponent(IChatComponent message) {
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

        OnChatMessageEventData chatMessage = tryParsePureChatMessage(components);

        if(chatMessage != null) {
            chatMessage.chatTypes.addAll(chatTypes);
        }

        return chatMessage;
    }

    private OnChatMessageEventData tryParsePureChatMessage(List<IChatComponent> remainingComponents) {
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
            return new OnChatMessageEventData(new PlayerName(tagComponent, name), lastComponent.getChatStyle(), message);
        return new OnChatMessageEventData(new PlayerName(name), lastComponent.getChatStyle(), message);
    }

    private static final IChatComponent partyChatComponent = blue(new ChatComponentText("Party \u003e "));
    public boolean isPartyChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, partyChatComponent);
    }

    private static final IChatComponent guildChatComponent = darkGreen(new ChatComponentText("Guild \u003e "));
    public boolean isGuildChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, guildChatComponent);
    }

    private static final IChatComponent shoutChatComponent = gold(new ChatComponentText("[SHOUT] "));
    public boolean isShoutChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, shoutChatComponent);
    }

    private static final IChatComponent teamCopsChatComponent = darkAqua(new ChatComponentText("[銐]"));
    public boolean isTeamCopsChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamCopsChatComponent);
    }

    private static final IChatComponent teamCrimsChatComponent = darkRed(new ChatComponentText("[銑]"));
    public boolean isTeamCrimsChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamCrimsChatComponent);
    }

    private static final IChatComponent teamUhcChatComponent = green(new ChatComponentText("[TEAM] "));
    public boolean isTeamUhcChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamUhcChatComponent);
    }

    private static final IChatComponent teamSpectatorChatComponent = gray(new ChatComponentText("[SPECTATOR] "));
    public boolean isTeamSpectatorChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamSpectatorChatComponent);
    }

    private static final IChatComponent teamRedChatComponent = red(new ChatComponentText("[RED] "));
    public boolean isTeamRedChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamRedChatComponent);
    }

    private static final IChatComponent teamGreenChatComponent = green(new ChatComponentText("[GREEN] "));
    public boolean isTeamGreenChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamGreenChatComponent);
    }

    private static final IChatComponent teamBlueChatComponent = blue(new ChatComponentText("[BLUE] "));
    public boolean isTeamBlueChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamBlueChatComponent);
    }

    private static final IChatComponent teamYellowChatComponent = yellow(new ChatComponentText("[YELLOW] "));
    public boolean isTeamYellowChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamYellowChatComponent);
    }

    private static final IChatComponent teamVampiresChatComponent = red(new ChatComponentText("[V] "));
    public boolean isTeamVampiresChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamVampiresChatComponent);
    }

    private static final IChatComponent teamSurvivorsChatComponent = blue(new ChatComponentText("[S] "));
    public boolean isTeamSurvivorsChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamSurvivorsChatComponent);
    }

    private static final IChatComponent teamHuntersChatComponent = gold(new ChatComponentText("[H]"));
    public boolean isTeamHuntersChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamHuntersChatComponent);
    }

    private static final IChatComponent teamAnimalsChatComponent = aqua(new ChatComponentText("[A]"));
    public boolean isTeamAnimalsChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, teamAnimalsChatComponent);
    }

    private static final IChatComponent msgFromChatComponent = lightPurple(new ChatComponentText("From "));
    public boolean isMsgFromChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, msgFromChatComponent);
    }

    private static final IChatComponent msgToChatComponent = lightPurple(new ChatComponentText("To "));
    public boolean isMsgToChatComponent(IChatComponent component) {
        return ChatUtilities.compareChatComponent(component, msgToChatComponent);
    }
}
