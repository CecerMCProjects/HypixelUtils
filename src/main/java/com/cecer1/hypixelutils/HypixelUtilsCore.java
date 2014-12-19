package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.commands.*;
import com.cecer1.hypixelutils.config.IConfigManager;
import com.cecer1.hypixelutils.events.*;
import com.cecer1.modframework.common.Scheduler;
import com.cecer1.modframework.common.commands.ICommandRegister;
import com.cecer1.modframework.common.events.EventManager;
import net.minecraft.command.ICommand;

public class HypixelUtilsCore {
    public static final String MODID = "hypixelutils";
    public static final String VERSION = "1.1.0";

    public static IConfigManager config;
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
    public static void registerCommand(ICommand command) {
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

    private static void registerCommands() {
        for(String commandName : config.getGuildChatToggleCommands()) {
            registerCommand(new GuildChatToggleCommand(commandName));
            registerCommand(new GuildChatToggleCommand("hypixeutils:" + commandName));
        }

        for(String commandName : config.getImprovedLobbyCommands()) {
            registerCommand(new ImprovedLobbyCommand(commandName));
            registerCommand(new ImprovedLobbyCommand("hypixeutils:" + commandName));
        }

        for(String commandName : config.getInstantBedCommands()) {
            registerCommand(new InstantBedToggleCommand(commandName));
            registerCommand(new InstantBedToggleCommand("hypixeutils:" + commandName));
        }

        for(String commandName : config.getLobbyProtectionCommands()) {
            registerCommand(new LobbyProtectionToggleCommand(commandName));
            registerCommand(new LobbyProtectionToggleCommand("hypixeutils:" + commandName));
        }

        for(String commandName : config.getPartyAutoRemoveCommands()) {
            registerCommand(new PartyAutoRemoveToggleCommand(commandName));
            registerCommand(new PartyAutoRemoveToggleCommand("hypixeutils:" + commandName));
        }

        for(String commandName : config.getPartyChatToggleCommands()) {
            registerCommand(new PartyChatToggleCommand(commandName));
            registerCommand(new PartyChatToggleCommand("hypixeutils:" + commandName));
        }
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
}
