package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.commands.GuildChatToggleCommand;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = HypixelUtils.MODID, version = HypixelUtils.VERSION)
public class HypixelUtils
{
    public static final String MODID = "hypixelutils";
    public static final String VERSION = "1.0.0";
	
    @Mod.Instance("hypixelutils")
    public static HypixelUtils instance;
	
	public static Configuration configFile;
    public static void syncConfig() {
        if(configFile.hasChanged())
            configFile.save();
    }
	
	public FilterGuildChatProcessor filterGuildChatProcessor;
	
    public HypixelUtils()
    {
		filterGuildChatProcessor = new FilterGuildChatProcessor();  
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent  event)
    {
        configFile = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
    }
	
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
		filterGuildChatProcessor = new FilterGuildChatProcessor();
        MinecraftForge.EVENT_BUS.register(filterGuildChatProcessor);
		
		ClientCommandHandler.instance.registerCommand(new GuildChatToggleCommand());
    }
}