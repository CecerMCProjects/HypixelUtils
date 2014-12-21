package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.config.DummyConfigManager;
import com.cecer1.modframework.common.Scheduler;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.liteloader.commands.LiteLoaderCommandRegister;
import com.cecer1.modframework.liteloader.events.adapters.LiteLoaderOnBungeeServerChangeAdapter;
import com.cecer1.modframework.liteloader.events.adapters.LiteLoaderOnChatAdapter;
import com.cecer1.modframework.liteloader.events.adapters.LiteLoaderOnTickAdapter;
import com.mumfrey.liteloader.ChatFilter;
import com.mumfrey.liteloader.OutboundChatFilter;
import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

import java.io.File;

public class LiteModHypixelUtils implements ChatFilter, Tickable, OutboundChatFilter {


    public static LiteModHypixelUtils instance;

    private LiteLoaderOnBungeeServerChangeAdapter liteloaderOnBungeeServerChangeAdapter;
    private LiteLoaderOnChatAdapter liteloaderOnChatAdapter;
    private LiteLoaderOnTickAdapter liteloaderOnTickAdapter;
    LiteLoaderCommandRegister liteloaderCommandRegister;

    public LiteModHypixelUtils() {
        instance = this;
    }

    @Override
    public boolean onChat(IChatComponent chat, String message, LiteLoaderEventBroker.ReturnValue<IChatComponent> newMessage) {
        return liteloaderOnChatAdapter.trigger(chat, message, newMessage);
    }

    @Override
    public boolean onSendChatMessage(String message) {
        return liteloaderCommandRegister.trigger(message);
    }

    @Override
    public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
        liteloaderOnTickAdapter.trigger(minecraft, partialTicks, inGame, clock);
    }

    @Override
    public String getVersion() {
        return HypixelUtilsCore.VERSION;
    }

    @Override
    public void init(File configPath) {
        liteloaderCommandRegister = new LiteLoaderCommandRegister();
        HypixelUtilsCore.commandRegister = liteloaderCommandRegister;

        HypixelUtilsCore.config = new DummyConfigManager();
        HypixelUtilsCore.scheduler = new Scheduler();
        HypixelUtilsCore.eventManager = new EventManager();

        HypixelUtilsCore.init();


        liteloaderOnBungeeServerChangeAdapter = new LiteLoaderOnBungeeServerChangeAdapter(HypixelUtilsCore.eventManager);
        liteloaderOnChatAdapter = new LiteLoaderOnChatAdapter(HypixelUtilsCore.eventManager);
        liteloaderOnTickAdapter = new LiteLoaderOnTickAdapter(HypixelUtilsCore.eventManager);
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath) {

    }

    @Override
    public String getName() {
        return HypixelUtilsCore.MODID;
    }
}
