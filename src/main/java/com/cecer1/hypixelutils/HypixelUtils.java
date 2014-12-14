package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.chatprocessors.AntiLobbyCommandProtectionProcessor;
import com.cecer1.hypixelutils.chatprocessors.DebugChatProcessor;
import com.cecer1.hypixelutils.chatprocessors.FilterGuildChatProcessor;
import com.cecer1.hypixelutils.chatprocessors.FilterPartyChatProcessor;
import com.cecer1.hypixelutils.commands.GuildChatToggleCommand;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

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
	
	public DebugChatProcessor debugChatProcessor;
	public FilterGuildChatProcessor filterGuildChatProcessor;
	public FilterPartyChatProcessor filterPartyChatProcessor;
	public AntiLobbyCommandProtectionProcessor antiLobbyCommandProtectionProcessor;

    public HypixelUtils()
    {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent  event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        debugChatProcessor = new DebugChatProcessor(config.get(config.CATEGORY_GENERAL, "[Debug] Dump chat JSON", false, "If true chat formatting will be dumped to STDOUT. Used for development - leave false for normal usage."), false);

        filterGuildChatProcessor = new FilterGuildChatProcessor(config.get(config.CATEGORY_GENERAL, "Hide guild chat", false, "If true then guild chat will be hidden."), false);
        filterPartyChatProcessor = new FilterPartyChatProcessor(config.get(config.CATEGORY_GENERAL, "Hide party chat", false, "If true then party chat will be hidden."), false);
        antiLobbyCommandProtectionProcessor = new AntiLobbyCommandProtectionProcessor(config.get(config.CATEGORY_GENERAL, "Disable /lobby protection", false, "If true then /lobby will not have to be confirmed."), false);

        syncConfig();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        FMLCommonHandler.instance().bus().register(instance);

        MinecraftForge.EVENT_BUS.register(debugChatProcessor);
        MinecraftForge.EVENT_BUS.register(filterGuildChatProcessor);
        MinecraftForge.EVENT_BUS.register(filterPartyChatProcessor);
        MinecraftForge.EVENT_BUS.register(antiLobbyCommandProtectionProcessor);

		ClientCommandHandler.instance.registerCommand(new GuildChatToggleCommand());
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
        if(eventArgs.modID.equals(HypixelUtils.MODID))
            syncConfig();
    }
}