package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.config.DummyConfigManager;
import com.cecer1.modframework.common.Scheduler;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.forge.commands.ForgeCommandRegister;
import com.cecer1.modframework.forge.events.adapters.ForgeOnBungeeServerChangeAdapter;
import com.cecer1.modframework.forge.events.adapters.ForgeOnChatAdapter;
import com.cecer1.modframework.forge.events.adapters.ForgeOnTickAdapter;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = HypixelUtilsCore.MODID, version = HypixelUtilsCore.VERSION)
public class HypixelUtils
{
    @Mod.Instance(HypixelUtilsCore.MODID)
    public static HypixelUtils instance;

    private ForgeOnBungeeServerChangeAdapter forgeOnBungeeServerChangeAdapter;
    private ForgeOnChatAdapter forgeOnChatAdapter;
    private ForgeOnTickAdapter forgeOnTickAdapter;

    public HypixelUtils()
    {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        HypixelUtilsCore.commandRegister = new ForgeCommandRegister();

        HypixelUtilsCore.config = new DummyConfigManager();
        HypixelUtilsCore.scheduler = new Scheduler();
        HypixelUtilsCore.eventManager = new EventManager();

        HypixelUtilsCore.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        forgeOnBungeeServerChangeAdapter = new ForgeOnBungeeServerChangeAdapter(HypixelUtilsCore.eventManager);
        forgeOnChatAdapter = new ForgeOnChatAdapter(HypixelUtilsCore.eventManager);
        forgeOnTickAdapter = new ForgeOnTickAdapter(HypixelUtilsCore.eventManager);

        FMLCommonHandler.instance().bus().register(instance);
        FMLCommonHandler.instance().bus().register(forgeOnTickAdapter);

        MinecraftForge.EVENT_BUS.register(forgeOnBungeeServerChangeAdapter);
        MinecraftForge.EVENT_BUS.register(forgeOnChatAdapter);
    }
}