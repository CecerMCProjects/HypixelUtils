package com.cecer1.hypixelutils.chat;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.utils.ChatUtilities;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.UUID;

import static com.cecer1.hypixelutils.utils.ChatUtilities.QuickFormatting.*;

public class ChatOutputs {
    private static final IChatComponent _bypassLobbyProtectionEnabledStatusTrue = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Bypassing /lobby protection has been ")))
            .appendSibling(green(new ChatComponentText("ENABLED")))
            .appendSibling(yellow(new ChatComponentText(".")));
    private static final IChatComponent _bypassLobbyProtectionEnabledStatusFalse = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Bypassing /lobby protection has been ")))
            .appendSibling(red(new ChatComponentText("DISABLED")))
            .appendSibling(yellow(new ChatComponentText(".")));
    public static void printBypassLobbyProtectionEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_bypassLobbyProtectionEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_bypassLobbyProtectionEnabledStatusFalse);
    }


    private static final IChatComponent _debugLoadingCloudConfigStart = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("Loading config for config key ")));
    private static final IChatComponent _debugLoadingCloudConfigEnd = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("...")));
    public static void printDebugLoadingCloudConfig(UUID key) {
        IChatComponent message = new ChatComponentText("");
        message.appendSibling(_debugLoadedCloudConfigStart);
        message.appendSibling(red(new ChatComponentText(key.toString())));
        message.appendSibling(_debugLoadingCloudConfigEnd);
        ChatUtilities.printDebugChatComponent(message);
    }

    private static final IChatComponent _debugLoadedCloudConfigStart = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("Loaded config for config key ")));
    private static final IChatComponent _debugLoadedCloudConfigEnd = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("!")));
    public static void printDebugLoadedCloudConfig(UUID key) {
        IChatComponent message = new ChatComponentText("");
        message.appendSibling(_debugLoadedCloudConfigStart);
        message.appendSibling(red(new ChatComponentText(key.toString())));
        message.appendSibling(_debugLoadedCloudConfigEnd);
        ChatUtilities.printDebugChatComponent(message);
    }

    private static final IChatComponent _debugSavingCloudConfigStart = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("Saving config for config key ")));
    private static final IChatComponent _debugSavingCloudConfigEnd = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("...")));
    public static void printDebugSavingCloudConfig(UUID key) {
        IChatComponent message = new ChatComponentText("");
        message.appendSibling(_debugSavingCloudConfigStart);
        message.appendSibling(red(new ChatComponentText(key.toString())));
        message.appendSibling(_debugSavingCloudConfigEnd);
        ChatUtilities.printDebugChatComponent(message);
    }

    private static final IChatComponent _debugSavedCloudConfigStart = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("Saved config for config key ")));
    private static final IChatComponent _debugSavedCloudConfigEnd = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("!")));
    public static void printDebugSavedCloudConfig(UUID key) {
        IChatComponent message = new ChatComponentText("");
        message.appendSibling(_debugSavingCloudConfigStart);
        message.appendSibling(red(new ChatComponentText(key.toString())));
        message.appendSibling(_debugSavedCloudConfigEnd);
        ChatUtilities.printDebugChatComponent(message);
    }

    private static final IChatComponent _errorConfigKeyNotUuid = new ChatComponentText("")
            .appendSibling(red(new ChatComponentText("ERROR: Invalid config key! Must be a valid UUID!")));
    public static void printErrorConfigKeyNotUuid() {
        ChatUtilities.printChatComponent(_errorConfigKeyNotUuid);
    }

    private static final IChatComponent _newConfigKeyStart = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Your new config key is ")));
    private static final IChatComponent _newConfigKeyEnd = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText(".")));
    public static void printNewConfigKey(UUID newKey) {
        IChatComponent message = new ChatComponentText("");
        message.appendSibling(_newConfigKeyStart);
        message.appendSibling(red(new ChatComponentText(newKey.toString())));
        message.appendSibling(_newConfigKeyEnd);
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _currentConfigKeyStart = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Your current config key is ")));
    private static final IChatComponent _currentConfigKeyEnd = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText(".")));
    public static void printCurrentConfigKey(UUID currentKey) {
        IChatComponent message = new ChatComponentText("");
        message.appendSibling(_currentConfigKeyStart);
        message.appendSibling(red(new ChatComponentText(currentKey.toString())));
        message.appendSibling(_currentConfigKeyEnd);
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _forcedConfigReload = new ChatComponentText("")
            .appendSibling(green(new ChatComponentText("Config reloaded!")));
    public static void printForcedConfigReload() {
        ChatUtilities.printDebugChatComponent(_forcedConfigReload, true);
    }

    private static final IChatComponent _forcingConfigReload = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Forcing config reload...")));
    public static void printForcingConfigReload() {
        ChatUtilities.printDebugChatComponent(_forcingConfigReload, true);
    }

    private static final IChatComponent _forcingConfigSave = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Forcing config save...")));
    public static void printForcingConfigSave() {
        ChatUtilities.printDebugChatComponent(_forcingConfigSave, true);
    }
    
    private static final IChatComponent _forcedConfigSave = new ChatComponentText("")
            .appendSibling(green(new ChatComponentText("Config saved!")));
    public static void printForcedConfigSave() {
        ChatUtilities.printDebugChatComponent(_forcedConfigSave, true);
    }

    private static final IChatComponent _configDeleting = new ChatComponentText("")
            .appendSibling(red(new ChatComponentText("Deleting all config data from server...")));
    public static void printConfigDeleting() {
        ChatUtilities.printDebugChatComponent(_configDeleting, true);
    }
    private static final IChatComponent _configDeleted = new ChatComponentText("")
            .appendSibling(red(new ChatComponentText("Deleted all config data from server!")));
    public static void printConfigDeleted() {
        ChatUtilities.printDebugChatComponent(_configDeleted, true);
    }


    private static final IChatComponent _debugDebugModeEnabledStatusTrue = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("Debug mode ")))
            .appendSibling(green(new ChatComponentText("ENABLED")))
            .appendSibling(gray(new ChatComponentText(".")));
    private static final IChatComponent _debugDebugModeEnabledStatusFalse = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("Debug mode ")))
            .appendSibling(red(new ChatComponentText("DISABLED")))
            .appendSibling(gray(new ChatComponentText(".")));
    public static void printDebugDebugModeEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printDebugChatComponent(_debugDebugModeEnabledStatusTrue, true);
        else
            ChatUtilities.printDebugChatComponent(_debugDebugModeEnabledStatusFalse, true);
    }

    private static final IChatComponent _debugServerNameSetTo = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Server Name set to ")));
    public static void printDebugServerNameSetTo(String name) {
        IChatComponent message = new ChatComponentText("");
        message.appendSibling(_debugServerNameSetTo);
        message.appendSibling(green(new ChatComponentText(name)));
        ChatUtilities.printDebugChatComponent(message);
    }

    private static final IChatComponent _debugMapNameSetTo = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Map Name set to ")));
    public static void printDebugMapNameSetTo(String name) {
        IChatComponent message = _debugMapNameSetTo.createCopy()
                .appendSibling(green(new ChatComponentText(name)));
        ChatUtilities.printDebugChatComponent(message);
    }

    private static final IChatComponent _debugProxySetTo = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Proxy Name set to ")));
    public static void printDebugProxyNameSetTo(String name) {
        IChatComponent message = _debugProxySetTo.createCopy()
                .appendSibling(green(new ChatComponentText(name)));
        ChatUtilities.printDebugChatComponent(message);
    }

    private static final IChatComponent _debugJobsQueueReset = new ChatComponentText("")
            .appendSibling(red(new ChatComponentText("Job queue reset!")));
    public static void printDebugJobsQueueReset() {
        ChatUtilities.printDebugChatComponent(_debugJobsQueueReset, true);
    }


    private static final IChatComponent _filterGuildChatEnabledStatusTrue = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Guild Chat is now ")))
            .appendSibling(red(new ChatComponentText("HIDDEN")))
            .appendSibling(yellow(new ChatComponentText(".")));
    private static final IChatComponent _filterGuildChatEnabledStatusFalse = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Guild Chat is now ")))
            .appendSibling(green(new ChatComponentText("SHOWN")))
            .appendSibling(yellow(new ChatComponentText(".")));
    public static void printFilterGuildChatEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_filterGuildChatEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_filterGuildChatEnabledStatusFalse);
    }


    private static final IChatComponent _filterPartyChatEnabledStatusTrue = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Party Chat is now ")))
            .appendSibling(red(new ChatComponentText("HIDDEN")))
            .appendSibling(yellow(new ChatComponentText(".")));
    private static final IChatComponent _filterPartyChatEnabledStatusFalse = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Party Chat is now ")))
            .appendSibling(green(new ChatComponentText("SHOWN")))
            .appendSibling(yellow(new ChatComponentText(".")));
    public static void printFilterPartyChatEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_filterPartyChatEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_filterPartyChatEnabledStatusFalse);
    }

    private static final IChatComponent _hypixelDetectedStart = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Hypixel Network detected! You are running HypixelUtils version ")));
    private static final IChatComponent _hypixelDetectedEnd = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText(".")));
    private static final ChatStyle _clickToOpenLicenseStyle = new ChatStyle()
            .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hypixelutils:license"))
            .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    white(new ChatComponentText("Click to view license information."))));
    public static void printHypixelDetected(String version) {
        String welcomeMessageJson = HypixelUtilsCore.configHelper.welcomeMessage.getValue();
        IChatComponent message;
        
        if(welcomeMessageJson == null) {
            message = new ChatComponentText("")
                    .setChatStyle(_clickToOpenLicenseStyle)
                    .appendSibling(_hypixelDetectedStart)
                    .appendSibling(darkAqua(new ChatComponentText(version)))
                    .appendSibling(_hypixelDetectedEnd);
        } else {
            welcomeMessageJson = welcomeMessageJson.replace("$VERSION$", version);
            message = ChatUtilities.convertJsonToChatComponent(welcomeMessageJson);
        }
        ChatUtilities.printChatComponent(message);
    }

    private static final IChatComponent _debugCurrentSavedConfigKeyStart = new ChatComponentText("")
            .appendSibling(gray(new ChatComponentText("Current saved config key is ")));
    public static void printDebugCurrentSavedConfigKey(UUID key) {
        IChatComponent message = _debugCurrentSavedConfigKeyStart.createCopy()
                .appendSibling(red(new ChatComponentText(key.toString())));
        ChatUtilities.printDebugChatComponent(message, true);
    }


    private static final IChatComponent _instantBedEnabledStatusTrue = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Instant Bed is now ")))
            .appendSibling(green(new ChatComponentText("ENABLED")))
            .appendSibling(yellow(new ChatComponentText(".")));
    private static final IChatComponent _instantBedEnabledStatusFalse = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Instant Bed is now ")))
            .appendSibling(red(new ChatComponentText("DISABLED")))
            .appendSibling(yellow(new ChatComponentText(".")));
    public static void printInstantBedEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_instantBedEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_instantBedEnabledStatusFalse);
    }


    private static final IChatComponent _partyAutoRemoveEnabledStatusTrue = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Offline party members ")))
            .appendSibling(green(new ChatComponentText("WILL")))
            .appendSibling(yellow(new ChatComponentText(" be automatically removed if they stop the party from joining a game.")));
    private static final IChatComponent _partyAutoRemoveEnabledStatusFalse = new ChatComponentText("")
            .appendSibling(yellow(new ChatComponentText("Offline party members ")))
            .appendSibling(red(new ChatComponentText("WILL NOT")))
            .appendSibling(yellow(new ChatComponentText(" be automatically removed if they stop the party from joining a game.")));
    public static void printPartyAutoRemoveEnabledStatus(boolean status) {
        if(status)
            ChatUtilities.printChatComponent(_partyAutoRemoveEnabledStatusTrue);
        else
            ChatUtilities.printChatComponent(_partyAutoRemoveEnabledStatusFalse);
    }
    private static final IChatComponent _partyAutoRemovedNotice = new ChatComponentText("")
        .appendSibling(yellow(new ChatComponentText(" has been automatically removed from the party because they were offline!")));
    public static void printPartyAutoRemovedNotice(String offlinePlayerName) {
        IChatComponent message = new ChatComponentText("")
                .appendSibling(yellow(new ChatComponentText(offlinePlayerName)))
                .appendSibling(_partyAutoRemovedNotice);
        ChatUtilities.printChatComponent(message);
    }

    
    private static final IChatComponent _errorTipAndThankCommandUsage = new ChatComponentText("")
            .appendSibling(darkRed(new ChatComponentText("Usage: /<command> <playername>")));
    public static void printErrorTipAndThankCommandUsage() {
        ChatUtilities.printChatComponent(_errorTipAndThankCommandUsage);
    }

    private static final IChatComponent _errorTipAndThankAllCommandDisabled = new ChatComponentText("")
            .appendSibling(darkRed(new ChatComponentText("Error: ")))
            .appendSibling(red(new ChatComponentText("\"/tipandthank all\" is disabled because \"/tip all\" was disabled.")));
    public static void printErrorTipAndThankAllCommandDisabled() {
        ChatUtilities.printChatComponent(_errorTipAndThankAllCommandDisabled);
    }
}
