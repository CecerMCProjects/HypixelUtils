package com.cecer1.hypixelutils;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = HypixelUtils.MODID, version = HypixelUtils.VERSION)
public class HypixelUtils
{
    public static final String MODID = "hypixelutils";
    public static final String VERSION = "1.0.0";
	
	public static Configuration configFile;
	
    @Mod.Instance("hypixelutils")
    public static HypixelUtils instance;

    public HypixelUtils()
    {
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent  event)
    {
        configFile = new Configuration(event.getSuggestedConfigurationFile());
		
		syncConfig();
    }
    public static void syncConfig() {
        if(configFile.hasChanged())
            configFile.save();
    }
}