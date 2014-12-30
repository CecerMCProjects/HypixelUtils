package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.commands.*;
import com.cecer1.hypixelutils.config.IConfigManager;
import com.cecer1.hypixelutils.events.*;
import com.cecer1.modframework.common.Scheduler;
import com.cecer1.modframework.common.commands.ICommandRegister;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.liteloader.commands.ICecerCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class HypixelUtilsCore {
    public static final String MODID = "hypixelutils";
    public static final String VERSION = "1.2.0";
    public static final String CONFIG_SERVER = "http://hypixelutils.cecer1.com:8014";
    //public static final String CONFIG_SERVER = "http://localhost:8014";

    public static IConfigManager config;
    private static Path _configKeyPath;
    public static Scheduler scheduler;
    public static EventManager eventManager;

    public static BypassLobbyCommandProtectionProcessor bypassLobbyCommandProtectionProcessor;
    public static DebugProcessor debugProcessor;
    public static FilterGuildChatProcessor filterGuildChatProcessor;
    public static FilterPartyChatProcessor filterPartyChatProcessor;
    public static InstantBedProcessor instantBedProcessor;
    public static ImprovedLobbyCommandProcessor improvedLobbyCommandProcessor;
    public static PartyAutoRemoveOfflineProcessor partyAutoRemoveOfflineProcessor;

    public static ICommandRegister commandRegister;
    public static void registerCommand(ICecerCommand command) {
        commandRegister.registerCommand(command);
    }

    public static void init() {
        scheduler = new Scheduler();

        bypassLobbyCommandProtectionProcessor = new BypassLobbyCommandProtectionProcessor();
        debugProcessor = new DebugProcessor();
        filterGuildChatProcessor = new FilterGuildChatProcessor();
        filterPartyChatProcessor = new FilterPartyChatProcessor();
        instantBedProcessor = new InstantBedProcessor();
        improvedLobbyCommandProcessor = new ImprovedLobbyCommandProcessor();
        partyAutoRemoveOfflineProcessor = new PartyAutoRemoveOfflineProcessor();
        registerEvents();
        registerCommands();
    }

    public static void setSavedConfigKey(UUID key) {
        try {
            Files.write(_configKeyPath, key.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static UUID getSavedConfigKey() {
        try {
            return UUID.fromString(new String(Files.readAllBytes(_configKeyPath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void registerCommands() {
        for(String commandName : config.getGuildChatToggleCommands()) {
            registerCommand(new GuildChatToggleCommand(commandName));
            registerCommand(new GuildChatToggleCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getImprovedLobbyCommands()) {
            registerCommand(new ImprovedLobbyCommand(commandName));
            registerCommand(new ImprovedLobbyCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getInstantBedCommands()) {
            registerCommand(new InstantBedToggleCommand(commandName));
            registerCommand(new InstantBedToggleCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getLobbyProtectionCommands()) {
            registerCommand(new LobbyProtectionToggleCommand(commandName));
            registerCommand(new LobbyProtectionToggleCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getPartyAutoRemoveCommands()) {
            registerCommand(new PartyAutoRemoveToggleCommand(commandName));
            registerCommand(new PartyAutoRemoveToggleCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getPartyChatToggleCommands()) {
            registerCommand(new PartyChatToggleCommand(commandName));
            registerCommand(new PartyChatToggleCommand("hypixelutils:" + commandName));
        }

        registerCommand(new LoadConfigCommand("hypixelutils:loadconfig"));
        registerCommand(new SaveConfigCommand("hypixelutils:saveconfig"));
        registerCommand(new ConfigKeyCommand("hypixelutils:configkey"));
        registerCommand(new DebugCommand("hypixelutils:debug"));
    }

    private static void registerEvents() {
        eventManager.registerEventHandlers(scheduler);

        eventManager.registerEventHandlers(bypassLobbyCommandProtectionProcessor);
        eventManager.registerEventHandlers(debugProcessor);
        eventManager.registerEventHandlers(filterGuildChatProcessor);
        eventManager.registerEventHandlers(filterPartyChatProcessor);
        eventManager.registerEventHandlers(instantBedProcessor);
        eventManager.registerEventHandlers(improvedLobbyCommandProcessor);
        eventManager.registerEventHandlers(partyAutoRemoveOfflineProcessor);
    }

    public static void initCloudConfig(Path path) {
        _configKeyPath = path;

        UUID configKey = HypixelUtilsCore.getSavedConfigKey();
        if(configKey == null) {
            configKey = UUID.randomUUID();
            HypixelUtilsCore.setSavedConfigKey(configKey);
        }
    }
}
