package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.chatprocessors.*;
import com.cecer1.hypixelutils.commands.*;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = HypixelUtils.MODID, version = HypixelUtils.VERSION, guiFactory = "com.cecer1.hypixelutils.HypixelUtilsGuiFactory")
public class HypixelUtils
{
    public static final String MODID = "hypixelutils";
    public static final String VERSION = "1.0.0";
	
    @Mod.Instance(HypixelUtils.MODID)
    public static HypixelUtils instance;
	
	public static Configuration config;
    public static void syncConfig() {
        if(config.hasChanged())
            config.save();
    }

    public Scheduler scheduler;

	public DebugChatProcessor debugChatProcessor;
	public FilterGuildChatProcessor filterGuildChatProcessor;
	public FilterPartyChatProcessor filterPartyChatProcessor;
	public AntiLobbyCommandProtectionProcessor antiLobbyCommandProtectionProcessor;
	public InstantBedChatProcessor instantBedChatProcessor;
	public LobbyAutoSwapProcessor lobbyAutoSwapProcessor;
	public PartyAutoRemoveChatProcessor partyAutoRemoveChatProcessor;

    public HypixelUtils()
    {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent  event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        scheduler = new Scheduler();

        debugChatProcessor = new DebugChatProcessor(config.get(config.CATEGORY_GENERAL, "[Debug] Dump chat JSON", false, "If true chat formatting will be dumped to STDOUT. Used for development - leave false for normal usage."), false);

        filterGuildChatProcessor = new FilterGuildChatProcessor(config.get(config.CATEGORY_GENERAL, "Hide guild chat", false, "If true then guild chat will be hidden."), false);
        filterPartyChatProcessor = new FilterPartyChatProcessor(config.get(config.CATEGORY_GENERAL, "Hide party chat", false, "If true then party chat will be hidden."), false);
        antiLobbyCommandProtectionProcessor = new AntiLobbyCommandProtectionProcessor(config.get(config.CATEGORY_GENERAL, "Disable /lobby protection", false, "If true then /lobby will not have to be confirmed."), false);
        instantBedChatProcessor = new InstantBedChatProcessor(config.get(config.CATEGORY_GENERAL, "Disable bed delay", false, "If true then clicking the bed in spectator mode will not wait 3 seconds before sending you to the lobby."), false);
        lobbyAutoSwapProcessor = new LobbyAutoSwapProcessor();
        partyAutoRemoveChatProcessor = new PartyAutoRemoveChatProcessor(config.get(config.CATEGORY_GENERAL, "Auto remove offline party members", false, "If true then offline party members will be automatically removed when they stop the party from joining a game."), false);

        syncConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(instance);
        FMLCommonHandler.instance().bus().register(scheduler);

        MinecraftForge.EVENT_BUS.register(debugChatProcessor);
        MinecraftForge.EVENT_BUS.register(filterGuildChatProcessor);
        MinecraftForge.EVENT_BUS.register(filterPartyChatProcessor);
        MinecraftForge.EVENT_BUS.register(antiLobbyCommandProtectionProcessor);
        MinecraftForge.EVENT_BUS.register(instantBedChatProcessor);
        MinecraftForge.EVENT_BUS.register(lobbyAutoSwapProcessor);
        MinecraftForge.EVENT_BUS.register(partyAutoRemoveChatProcessor);

		ClientCommandHandler.instance.registerCommand(new GuildChatToggleCommand());
		ClientCommandHandler.instance.registerCommand(new PartyChatToggleCommand());
		ClientCommandHandler.instance.registerCommand(new LobbyProtectionToggleCommand());
		ClientCommandHandler.instance.registerCommand(new InstantBedToggleCommand());
		ClientCommandHandler.instance.registerCommand(new ImprovedLobbyCommand("hypixelutils:lobby"));
		ClientCommandHandler.instance.registerCommand(new ImprovedLobbyCommand("hypixelutils:ilobby"));
		ClientCommandHandler.instance.registerCommand(new ImprovedLobbyCommand("ilobby"));
		ClientCommandHandler.instance.registerCommand(new PartyAutoRemoveToggleCommand());

        // Disabled due to tab sending command bug. Need to find a way to not send the tab request to the server.
		//ClientCommandHandler.instance.registerCommand(new ImprovedLobbyCommand("lobby"));
		//ClientCommandHandler.instance.registerCommand(new ImprovedLobbyCommand("hub"));
		//ClientCommandHandler.instance.registerCommand(new ImprovedLobbyCommand("leave"));
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(HypixelUtils.MODID))
            syncConfig();
    }
}