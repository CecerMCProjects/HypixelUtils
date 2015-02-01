package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.backgroundcommands.HypixelCommandJobManager;
import com.cecer1.hypixelutils.chat.ChatManager;
import com.cecer1.hypixelutils.clientcommands.ICommandRegister;
import com.cecer1.hypixelutils.data.config.ConfigHelper;
import com.cecer1.hypixelutils.data.config.ConfigManager;
import com.cecer1.hypixelutils.data.config.ConfigStore;
import com.cecer1.hypixelutils.events.EventManager;
import com.cecer1.hypixelutils.features.boosters.BoosterQueueChatModifier;
import com.cecer1.hypixelutils.features.boosters.TipAndThankChatModifier;
import com.cecer1.hypixelutils.features.boosters.TipAndThankCommand;
import com.cecer1.hypixelutils.features.bypasslobbyprotection.BypassLobbyProtectionCommand;
import com.cecer1.hypixelutils.features.bypasslobbyprotection.BypassLobbyProtectionProcessor;
import com.cecer1.hypixelutils.features.cloudconfig.*;
import com.cecer1.hypixelutils.features.debug.ChatPrinterProcessor;
import com.cecer1.hypixelutils.features.debug.DebugCommand;
import com.cecer1.hypixelutils.features.debug.DebugProcessor;
import com.cecer1.hypixelutils.features.debug.ResetJobsCommand;
import com.cecer1.hypixelutils.features.filterguildchat.FilterGuildChatCommand;
import com.cecer1.hypixelutils.features.filterguildchat.FilterGuildChatProcessor;
import com.cecer1.hypixelutils.features.filterpartychat.FilterPartyChatCommand;
import com.cecer1.hypixelutils.features.filterpartychat.FilterPartyChatProcessor;
import com.cecer1.hypixelutils.features.general.HandleServerJoinInit;
import com.cecer1.hypixelutils.features.improvedlobby.ImprovedLobbyCommand;
import com.cecer1.hypixelutils.features.improvedlobby.ImprovedLobbyCommandProcessor;
import com.cecer1.hypixelutils.features.instantbed.InstantBedCommand;
import com.cecer1.hypixelutils.features.instantbed.InstantBedProcessor;
import com.cecer1.hypixelutils.features.license.LicenseCommand;
import com.cecer1.hypixelutils.features.partyautoremove.PartyAutoRemoveProcessor;
import com.cecer1.hypixelutils.features.partyautoremove.PartyAutoRemoveToggleCommand;
import com.cecer1.hypixelutils.features.ragequit.RageQuitCommand;
import com.cecer1.hypixelutils.features.soundfx.SoundEffectsChatProcessor;
import com.cecer1.hypixelutils.gui.HypixelUtilsGuiManager;
import com.cecer1.hypixelutils.utils.ChatUtilities;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HypixelUtilsCore {
    public static final String MODID = "hypixelutils";
    public static final String VERSION = "1.2.3";
    public static final String CONFIG_SERVER = "http://www.cecer1.com/hypixelutils/cloudconfig";
    //public static final String CONFIG_SERVER = "http://hypixelutils.cecer1.com:8014";
    //public static final String CONFIG_SERVER = "http://localhost:8014";

    public static HypixelState currentState;

    public static ConfigManager internalConfig;
    public static ConfigHelper configHelper;
    public static CloudConfigServerGateway cloudConfigServerGateway;
    private static Path _configKeyPath;

    public static Scheduler scheduler;
    public static EventManager eventManager;
    public static ChatManager chatManager;
    public static HypixelCommandJobManager commandJobManager;

    public static BypassLobbyProtectionProcessor bypassLobbyCommandProtectionProcessor;
    public static DebugProcessor debugProcessor;
    public static FilterGuildChatProcessor filterGuildChatProcessor;
    public static FilterPartyChatProcessor filterPartyChatProcessor;
    public static InstantBedProcessor instantBedProcessor;
    public static ImprovedLobbyCommandProcessor improvedLobbyCommandProcessor;
    public static PartyAutoRemoveProcessor partyAutoRemoveOfflineProcessor;
    public static TipAndThankChatModifier tipAndThankChatModifier;
    public static BoosterQueueChatModifier boosterQueueChatModifier;

    public static ICommandRegister commandRegister;
    public static HypixelUtilsGuiManager userInterface;

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
        tipAndThankChatModifier = new TipAndThankChatModifier();
        boosterQueueChatModifier = new BoosterQueueChatModifier();
        
        chatManager = new ChatManager();
        commandJobManager = new HypixelCommandJobManager();
        
        userInterface = new HypixelUtilsGuiManager();

        registerEvents();
        registerCommands();
        
        configHelper.forceLoad();
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
    
    public static List<String> skipEventProcessingChatMessages = new ArrayList<String>();
    
    public static void sendChatMessage(String message) {
        sendChatMessage(message, true);
    }
    public static void sendChatMessage(String message, boolean skipEventProcessing) {
        if(skipEventProcessing) {
            skipEventProcessingChatMessages.add(message);
        }
        ChatUtilities.sendChat(message);
    }

    private static void registerCommands() {
        for(String commandName : configHelper.guildChatToggleCommands.getValue()) {
            commandRegister.registerCommand(new FilterGuildChatCommand(commandName));
            commandRegister.registerCommand(new FilterGuildChatCommand("hypixelutils:" + commandName));
        }

        for(String commandName : configHelper.improvedLobbyCommands.getValue()) {
            commandRegister.registerCommand(new ImprovedLobbyCommand(commandName));
            commandRegister.registerCommand(new ImprovedLobbyCommand("hypixelutils:" + commandName));
        }

        for(String commandName : configHelper.instantBedToggleCommands.getValue()) {
            commandRegister.registerCommand(new InstantBedCommand(commandName));
            commandRegister.registerCommand(new InstantBedCommand("hypixelutils:" + commandName));
        }

        for(String commandName : configHelper.bypassLobbyProtectionToggleCommands.getValue()) {
            commandRegister.registerCommand(new BypassLobbyProtectionCommand(commandName));
            commandRegister.registerCommand(new BypassLobbyProtectionCommand("hypixelutils:" + commandName));
        }

        for(String commandName : configHelper.partyAutoRemoveToggleCommands.getValue()) {
            commandRegister.registerCommand(new PartyAutoRemoveToggleCommand(commandName));
            commandRegister.registerCommand(new PartyAutoRemoveToggleCommand("hypixelutils:" + commandName));
        }

        for(String commandName : configHelper.partyChatToggleCommands.getValue()) {
            commandRegister.registerCommand(new FilterPartyChatCommand(commandName));
            commandRegister.registerCommand(new FilterPartyChatCommand("hypixelutils:" + commandName));
        }

        for(String commandName : configHelper.tipAndThankCommands.getValue()) {
            commandRegister.registerCommand(new TipAndThankCommand(commandName));
            commandRegister.registerCommand(new TipAndThankCommand("hypixelutils:" + commandName));
        }

        for(String commandName : configHelper.ragequitCommands.getValue()) {
            commandRegister.registerCommand(new RageQuitCommand(commandName));
            commandRegister.registerCommand(new RageQuitCommand("hypixelutils:" + commandName));
        }

        commandRegister.registerCommand(new ConfigGuiCommand("config"));
        commandRegister.registerCommand(new ConfigGuiCommand("hypixelutils:config"));

        commandRegister.registerCommand(new LoadConfigCommand("hypixelutils:loadconfig"));
        commandRegister.registerCommand(new SaveConfigCommand("hypixelutils:saveconfig"));
        commandRegister.registerCommand(new SaveConfigCommand("hypixelutils:deleteconfig"));
        commandRegister.registerCommand(new ConfigKeyCommand("hypixelutils:configkey"));
        commandRegister.registerCommand(new DebugCommand("hypixelutils:debug"));
        commandRegister.registerCommand(new ResetJobsCommand("hypixelutils:resetjobs"));
        
        commandRegister.registerCommand(new LicenseCommand("hypixelutils"));
        commandRegister.registerCommand(new LicenseCommand("hypixelutils:about"));
        commandRegister.registerCommand(new LicenseCommand("hypixelutils:legal"));
        commandRegister.registerCommand(new LicenseCommand("hypixelutils:license"));
        commandRegister.registerCommand(new LicenseCommand("hypixelutils:credits"));
    }

    private static void registerEvents() {
        eventManager.registerEventHandlers(scheduler);
        eventManager.registerEventHandlers(currentState);

        eventManager.registerEventHandlers(chatManager);

        eventManager.registerEventHandlers(commandJobManager);
        
        eventManager.registerEventHandlers(boosterQueueChatModifier);
        eventManager.registerEventHandlers(tipAndThankChatModifier);
        eventManager.registerEventHandlers(bypassLobbyCommandProtectionProcessor);
        eventManager.registerEventHandlers(new ChatPrinterProcessor());
        eventManager.registerEventHandlers(debugProcessor);
        eventManager.registerEventHandlers(filterGuildChatProcessor);
        eventManager.registerEventHandlers(filterPartyChatProcessor);
        eventManager.registerEventHandlers(new HandleServerJoinInit());
        eventManager.registerEventHandlers(improvedLobbyCommandProcessor);
        eventManager.registerEventHandlers(new SoundEffectsChatProcessor());
        eventManager.registerEventHandlers(instantBedProcessor);
        eventManager.registerEventHandlers(partyAutoRemoveOfflineProcessor);
        eventManager.registerEventHandlers(userInterface);
    }

    private static void initConfig() {
        for (int i = 0; i < 5; i++) {
            try {
                configHelper = new ConfigHelper();
                break;
            } catch (BufferUnderflowException e) {
                if (i == 4)
                    throw e;
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("###################################################################");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("");
                System.out.println("BufferUnderflowException when loading config. Retrying! (This is a workaround and not a proper fix! I will fix it in the future!)"); // TODO: Fix this!
                continue;
            }
        }
    }

    public static void initCloudConfig(Path path) throws CloudConfigServerGateway.CloudConfigServerException {
        _configKeyPath = path;
        UUID configKey = HypixelUtilsCore.getSavedConfigKey();
        if(configKey == null) {
            configKey = UUID.randomUUID();
            HypixelUtilsCore.setSavedConfigKey(configKey);
        }
        cloudConfigServerGateway = new CloudConfigServerGateway(getSavedConfigKey());
        internalConfig = new ConfigManager(new ConfigStore(cloudConfigServerGateway.getConfigMapFromServerSync()));
        initConfig();
    }
}
