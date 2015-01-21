package com.cecer1.hypixelutils.chat;

import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import java.util.UUID;

public class ChatOutputs {
    public static boolean debugMode = true;
    
    
    private static final IChatComponent _bypassLobbyProtectionEnabledStatusFalse = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Bypassing /lobby protection has been ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("DISABLED").setChatStyle(ChatUtilities.ChatPresets.RED))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    private static final IChatComponent _bypassLobbyProtectionEnabledStatusTrue = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Bypassing /lobby protection has been ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("ENABLED").setChatStyle(ChatUtilities.ChatPresets.GREEN))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printBypassLobbyProtectionEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_bypassLobbyProtectionEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_bypassLobbyProtectionEnabledStatusFalse);
    }


    private static final IChatComponent _debugLoadingCloudConfigStart = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Loading config for config key ").setChatStyle(ChatUtilities.ChatPresets.GRAY));
    private static final IChatComponent _debugLoadingCloudConfigEnd = new ChatComponentText("...").setChatStyle(ChatUtilities.ChatPresets.GRAY);
    public static void printDebugLoadingCloudConfig(UUID key) {
        if(!debugMode)
            return;
        
        IChatComponent message = _debugLoadingCloudConfigStart.createCopy()
                .appendSibling(new ChatComponentText(key.toString()).setChatStyle(ChatUtilities.ChatPresets.RED))
                .appendSibling(_debugLoadingCloudConfigEnd);
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _debugLoadedCloudConfigStart = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Loaded config for config key ").setChatStyle(ChatUtilities.ChatPresets.GRAY));
    private static final IChatComponent _debugLoadedCloudConfigEnd = new ChatComponentText("!").setChatStyle(ChatUtilities.ChatPresets.GRAY);
    public static void printDebugLoadedCloudConfig(UUID key) {
        if(!debugMode)
            return;
        
        IChatComponent message = _debugLoadedCloudConfigStart.createCopy()
                .appendSibling(new ChatComponentText(key.toString()).setChatStyle(ChatUtilities.ChatPresets.RED))
                .appendSibling(_debugLoadedCloudConfigEnd);
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _debugSavingCloudConfigStart = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Saving config for config key ").setChatStyle(ChatUtilities.ChatPresets.GRAY));
    private static final IChatComponent _debugSavingCloudConfigEnd = new ChatComponentText("...").setChatStyle(ChatUtilities.ChatPresets.GRAY);
    public static void printDebugSavingCloudConfig(UUID key) {
        if(!debugMode)
            return;

        IChatComponent message = _debugSavingCloudConfigStart.createCopy()
                .appendSibling(new ChatComponentText(key.toString()).setChatStyle(ChatUtilities.ChatPresets.RED))
                .appendSibling(_debugSavingCloudConfigEnd);
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _debugSavedCloudConfigStart = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Saved config for config key ").setChatStyle(ChatUtilities.ChatPresets.GRAY));
    private static final IChatComponent _debugSavedCloudConfigEnd = new ChatComponentText("!").setChatStyle(ChatUtilities.ChatPresets.GRAY);
    public static void printDebugSavedCloudConfig(UUID key) {
        if(!debugMode)
            return;

        IChatComponent message = _debugSavingCloudConfigStart.createCopy()
                .appendSibling(new ChatComponentText(key.toString()).setChatStyle(ChatUtilities.ChatPresets.RED))
                .appendSibling(_debugSavedCloudConfigEnd);
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _errorConfigNotSupportGui = UtilityMethods.getHypixelUtilsChatComponentPrefix().appendSibling(new ChatComponentText("ERROR: Config backend does provide GUI support!").setChatStyle(ChatUtilities.ChatPresets.RED));
    public static void printErrorConfigNotSupportGui() {
        ChatUtilities.printChatComponent(_errorConfigNotSupportGui);
    }

    private static final IChatComponent _errorConfigKeyNotUuid = UtilityMethods.getHypixelUtilsChatComponentPrefix().appendSibling(new ChatComponentText("ERROR: Invalid config key! Must be a valid UUID!").setChatStyle(ChatUtilities.ChatPresets.RED));
    public static void printErrorConfigKeyNotUuid() {
        ChatUtilities.printChatComponent(_errorConfigKeyNotUuid);
    }

    private static final IChatComponent _newConfigKeyStart = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Your new config key is ").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    private static final IChatComponent _newConfigKeyEnd = new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW);
    public static void printNewConfigKey(UUID newKey) {
        IChatComponent message = _newConfigKeyStart.createCopy()
                .appendSibling(new ChatComponentText(newKey.toString()).setChatStyle(ChatUtilities.ChatPresets.RED))
                .appendSibling(_newConfigKeyEnd);
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _currentConfigKeyStart = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Your current config key is ").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    private static final IChatComponent _currentConfigKeyEnd = new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW);
    public static void printCurrentConfigKey(UUID currentKey) {
        IChatComponent message = _currentConfigKeyStart.createCopy()
                .appendSibling(new ChatComponentText(currentKey.toString()).setChatStyle(ChatUtilities.ChatPresets.RED))
                .appendSibling(_currentConfigKeyEnd);
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _errorConfigNotCloudConfig = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("ERROR: Config backend is not using Cloud Config!").setChatStyle(ChatUtilities.ChatPresets.RED));
    public static void printErrorConfigNotCloudConfig() {
        ChatUtilities.printChatComponent(_errorConfigNotCloudConfig);
    }

    private static final IChatComponent _forcingConfigReload = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Forcing config reload...").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printForcingConfigReload() {
        ChatUtilities.printChatComponent(_forcingConfigReload);
    }

    private static final IChatComponent _errorConfigNotSupportForcedReload = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("ERROR: Config backend does not support forced reloading!").setChatStyle(ChatUtilities.ChatPresets.RED));
    public static void printErrorConfigNotSupportForcedReload() {
        ChatUtilities.printChatComponent(_errorConfigNotSupportForcedReload);
    }

    private static final IChatComponent _forcingConfigSave = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Forcing config save...").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printForcingConfigSave() {
        ChatUtilities.printChatComponent(_forcingConfigSave);
    }

    private static final IChatComponent _errorConfigNotSupportForcedSave = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("ERROR: Config backend does not support forced saving!").setChatStyle(ChatUtilities.ChatPresets.RED));
    public static void printErrorConfigNotSupportForcedSave() {
        ChatUtilities.printChatComponent(_errorConfigNotSupportForcedSave);
    }


    private static final IChatComponent _debugDebugModeEnabledStatusFalse = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Debug mode ").setChatStyle(ChatUtilities.ChatPresets.GRAY))
            .appendSibling(new ChatComponentText("DISABLED").setChatStyle(ChatUtilities.ChatPresets.RED))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    private static final IChatComponent _debugDebugModeEnabledStatusTrue = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Debug mode ").setChatStyle(ChatUtilities.ChatPresets.GRAY))
            .appendSibling(new ChatComponentText("ENABLED").setChatStyle(ChatUtilities.ChatPresets.GREEN))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printDebugDebugModeEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_debugDebugModeEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_debugDebugModeEnabledStatusFalse);
    }

    private static final IChatComponent _debugServerNameSetTo = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Server Name set to ").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printDebugServerNameSetTo(String name) {
        if(!debugMode)
            return;

        IChatComponent message = _debugServerNameSetTo.createCopy()
                .appendSibling(new ChatComponentText(name).setChatStyle(ChatUtilities.ChatPresets.GREEN));
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _debugMapNameSetTo = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Map Name set to ").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printDebugMapNameSetTo(String name) {
        if(!debugMode)
            return;

        IChatComponent message = _debugMapNameSetTo.createCopy()
                .appendSibling(new ChatComponentText(name).setChatStyle(ChatUtilities.ChatPresets.GREEN));
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _debugProxySetTo = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Proxy Name set to ").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printDebugProxyNameSetTo(String name) {
        if(!debugMode)
            return;

        IChatComponent message = _debugProxySetTo.createCopy()
                .appendSibling(new ChatComponentText(name).setChatStyle(ChatUtilities.ChatPresets.GREEN));
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _debugJobsQueueReset = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Job queue reset!").setChatStyle(ChatUtilities.ChatPresets.RED));
    public static void printDebugJobsQueueReset() {
        if(!debugMode)
            return;

        ChatUtilities.printChatComponent(_debugJobsQueueReset);
    }


    private static final IChatComponent _filterGuildChatEnabledStatusTrue = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Guild Chat is now ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("HIDDEN").setChatStyle(ChatUtilities.ChatPresets.RED))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    private static final IChatComponent _filterGuildChatEnabledStatusFalse = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Guild Chat is now ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("SHOWN").setChatStyle(ChatUtilities.ChatPresets.GREEN))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printFilterGuildChatEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_filterGuildChatEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_filterGuildChatEnabledStatusFalse);
    }


    private static final IChatComponent _filterPartyChatEnabledStatusTrue = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Party Chat is now ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("HIDDEN").setChatStyle(ChatUtilities.ChatPresets.RED))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    private static final IChatComponent _filterPartyChatEnabledStatusFalse = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Party Chat is now ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("SHOWN").setChatStyle(ChatUtilities.ChatPresets.GREEN))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printFilterPartyChatEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_filterPartyChatEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_filterPartyChatEnabledStatusFalse);
    }

    private static final IChatComponent _hypixelDetectedStart = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Hypixel Network detected! You are running HypixelUtils version ").setChatStyle(ChatUtilities.ChatPresets.YELLOW.createShallowCopy()
                    .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hypixelutils:license"))
                    .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ChatComponentText("Click to view license information.").setChatStyle(ChatUtilities.ChatPresets.WHITE)))));
    private static final IChatComponent _hypixelDetectedEnd = new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW);
    public static void printHypixelDetected(String version) {
        IChatComponent message = _hypixelDetectedStart.createCopy()
                .appendSibling(new ChatComponentText(version).setChatStyle(ChatUtilities.ChatPresets.DARK_AQUA))
                .appendSibling(_hypixelDetectedEnd);
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _debugCurrentSavedConfigKeyStart = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix()
            .appendSibling(new ChatComponentText("Current saved config key is ").setChatStyle(ChatUtilities.ChatPresets.GRAY));
    public static void printDebugCurrentSavedConfigKey(UUID key) {
        if(!debugMode)
            return;

        IChatComponent message = _debugCurrentSavedConfigKeyStart.createCopy()
                .appendSibling(new ChatComponentText(key.toString()).setChatStyle(ChatUtilities.ChatPresets.RED));
        ChatUtilities.printChatComponent(message);
    }


    private static final IChatComponent _instantBedEnabledStatusTrue = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Instant Bed is now ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("ENABLED").setChatStyle(ChatUtilities.ChatPresets.GREEN))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    private static final IChatComponent _instantBedEnabledStatusFalse = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Instant Bed is now ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("DISABLED").setChatStyle(ChatUtilities.ChatPresets.RED))
            .appendSibling(new ChatComponentText(".").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printInstantBedEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_instantBedEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_instantBedEnabledStatusFalse);
    }


    private static final IChatComponent _partyAutoRemoveEnabledStatusTrue = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Offline party members ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("WILL").setChatStyle(ChatUtilities.ChatPresets.GREEN))
            .appendSibling(new ChatComponentText(" be automatically removed if they stop the party from joining a game.").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    private static final IChatComponent _partyAutoRemoveEnabledStatusFalse = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Offline party members ").setChatStyle(ChatUtilities.ChatPresets.YELLOW))
            .appendSibling(new ChatComponentText("WILL NOT").setChatStyle(ChatUtilities.ChatPresets.RED))
            .appendSibling(new ChatComponentText(" be automatically removed if they stop the party from joining a game.").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printPartyAutoRemoveEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_partyAutoRemoveEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_partyAutoRemoveEnabledStatusFalse);
    }
    private static final IChatComponent _partyAutoRemovedNotice = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText(" has been automatically removed from the party because they were offline!").setChatStyle(ChatUtilities.ChatPresets.YELLOW));
    public static void printPartyAutoRemovedNotice(String offlinePlayerName) {
        IChatComponent message = new ChatComponentText(offlinePlayerName).setChatStyle(ChatUtilities.ChatPresets.YELLOW)
                .appendSibling(_partyAutoRemovedNotice);
        ChatUtilities.printChatComponent(message);
    }

    
    private static final IChatComponent _errorTipAndThankCommandUsage = UtilityMethods.getHypixelUtilsChatComponentPrefix()
            .appendSibling(new ChatComponentText("Usage: /<command> <playername>").setChatStyle(ChatUtilities.ChatPresets.DARK_RED));
    public static void printErrorTipAndThankCommandUsage() {
        ChatUtilities.printChatComponent(_errorTipAndThankCommandUsage);
    }
}
