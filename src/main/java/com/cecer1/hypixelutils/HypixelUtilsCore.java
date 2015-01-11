package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.chat.ChatManager;
import com.cecer1.hypixelutils.commands.HypixelCommandManager;
import com.cecer1.hypixelutils.config.IConfigManager;
import com.cecer1.hypixelutils.features.bypasslobbyprotection.BypassLobbyProtectionCommand;
import com.cecer1.hypixelutils.features.bypasslobbyprotection.BypassLobbyProtectionProcessor;
import com.cecer1.hypixelutils.features.cloudconfig.ConfigKeyCommand;
import com.cecer1.hypixelutils.features.cloudconfig.LoadConfigCommand;
import com.cecer1.hypixelutils.features.cloudconfig.SaveConfigCommand;
import com.cecer1.hypixelutils.features.debug.ChatPrinterProcessor;
import com.cecer1.hypixelutils.features.debug.DebugCommand;
import com.cecer1.hypixelutils.features.debug.DebugProcessor;
import com.cecer1.hypixelutils.features.filterguildchat.FilterGuildChatCommand;
import com.cecer1.hypixelutils.features.filterguildchat.FilterGuildChatProcessor;
import com.cecer1.hypixelutils.features.filterpartychat.FilterPartyChatCommand;
import com.cecer1.hypixelutils.features.filterpartychat.FilterPartyChatProcessor;
import com.cecer1.hypixelutils.features.improvedlobby.ImprovedLobbyCommand;
import com.cecer1.hypixelutils.features.improvedlobby.ImprovedLobbyCommandProcessor;
import com.cecer1.hypixelutils.features.instantbed.InstantBedCommand;
import com.cecer1.hypixelutils.features.instantbed.InstantBedProcessor;
import com.cecer1.hypixelutils.features.partyautoremove.PartyAutoRemoveProcessor;
import com.cecer1.hypixelutils.features.partyautoremove.PartyAutoRemoveToggleCommand;
import com.cecer1.modframework.common.Scheduler;
import com.cecer1.modframework.common.commands.ICommandRegister;
import com.cecer1.modframework.common.events.EventManager;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class HypixelUtilsCore {
    public static final String MODID = "hypixelutils";
    public static final String VERSION = "1.2.0";
    public static final String CONFIG_SERVER = "http://hypixelutils.cecer1.com:8014";
    //public static final String CONFIG_SERVER = "http://localhost:8014";

    public static HypixelState currentState;

    public static IConfigManager config;
    private static Path _configKeyPath;

    public static Scheduler scheduler;
    public static EventManager eventManager;
    public static ChatManager chatManager;
    public static HypixelCommandManager commandJobManager;

    public static BypassLobbyProtectionProcessor bypassLobbyCommandProtectionProcessor;
    public static DebugProcessor debugProcessor;
    public static FilterGuildChatProcessor filterGuildChatProcessor;
    public static FilterPartyChatProcessor filterPartyChatProcessor;
    public static InstantBedProcessor instantBedProcessor;
    public static ImprovedLobbyCommandProcessor improvedLobbyCommandProcessor;
    public static PartyAutoRemoveProcessor partyAutoRemoveOfflineProcessor;

    public static ICommandRegister commandRegister;

    public static void init() {
        currentState = new HypixelState();

        scheduler = new Scheduler();

        bypassLobbyCommandProtectionProcessor = new BypassLobbyProtectionProcessor();
        debugProcessor = new DebugProcessor();
        filterGuildChatProcessor = new FilterGuildChatProcessor();
        filterPartyChatProcessor = new FilterPartyChatProcessor();
        instantBedProcessor = new InstantBedProcessor();
        improvedLobbyCommandProcessor = new ImprovedLobbyCommandProcessor();
        partyAutoRemoveOfflineProcessor = new PartyAutoRemoveProcessor();


        chatManager = new ChatManager();
        commandJobManager = new HypixelCommandManager();

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
    
    public static void sendChatMessage(String message) {
        if(commandRegister.trigger(message))
            Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }

    private static void registerCommands() {
        for(String commandName : config.getGuildChatToggleCommands()) {
            commandRegister.registerCommand(new FilterGuildChatCommand(commandName));
            commandRegister.registerCommand(new FilterGuildChatCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getImprovedLobbyCommands()) {
            commandRegister.registerCommand(new ImprovedLobbyCommand(commandName));
            commandRegister.registerCommand(new ImprovedLobbyCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getInstantBedCommands()) {
            commandRegister.registerCommand(new InstantBedCommand(commandName));
            commandRegister.registerCommand(new InstantBedCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getLobbyProtectionCommands()) {
            commandRegister.registerCommand(new BypassLobbyProtectionCommand(commandName));
            commandRegister.registerCommand(new BypassLobbyProtectionCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getPartyAutoRemoveCommands()) {
            commandRegister.registerCommand(new PartyAutoRemoveToggleCommand(commandName));
            commandRegister.registerCommand(new PartyAutoRemoveToggleCommand("hypixelutils:" + commandName));
        }

        for(String commandName : config.getPartyChatToggleCommands()) {
            commandRegister.registerCommand(new FilterPartyChatCommand(commandName));
            commandRegister.registerCommand(new FilterPartyChatCommand("hypixelutils:" + commandName));
        }

        commandRegister.registerCommand(new LoadConfigCommand("hypixelutils:loadconfig"));
        commandRegister.registerCommand(new SaveConfigCommand("hypixelutils:saveconfig"));
        commandRegister.registerCommand(new ConfigKeyCommand("hypixelutils:configkey"));
        commandRegister.registerCommand(new DebugCommand("hypixelutils:debug"));
    }

    private static void registerEvents() {
        eventManager.registerEventHandlers(scheduler);

        eventManager.registerEventHandlers(bypassLobbyCommandProtectionProcessor);
        eventManager.registerEventHandlers(debugProcessor);
        eventManager.registerEventHandlers(instantBedProcessor);
        eventManager.registerEventHandlers(improvedLobbyCommandProcessor);
        eventManager.registerEventHandlers(partyAutoRemoveOfflineProcessor);

        chatManager.subscribe(filterGuildChatProcessor);
        chatManager.subscribe(filterPartyChatProcessor);
        chatManager.subscribe(new ChatPrinterProcessor());

        eventManager.registerEventHandlers(commandJobManager);
        chatManager.subscribe(commandJobManager);

        eventManager.registerEventHandlers(chatManager);
        eventManager.registerEventHandlers(currentState);

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
