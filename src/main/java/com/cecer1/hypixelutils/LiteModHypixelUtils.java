package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.config.CloudConfigManager;
import com.cecer1.modframework.common.Scheduler;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.common.utils.WorldDimension;
import com.cecer1.modframework.liteloader.commands.LiteLoaderCommandRegister;
import com.cecer1.modframework.liteloader.events.adapters.*;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.*;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.util.IChatComponent;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LiteModHypixelUtils implements ChatFilter, Tickable, OutboundChatFilter, JoinGameListener, PacketHandler {
    public static LiteModHypixelUtils instance;

    private LiteLoaderOnBungeeServerChangeAdapter liteloaderOnBungeeServerChangeAdapter;
    private LiteLoaderOnChatAdapter liteloaderOnChatAdapter;
    private LiteLoaderOnTickAdapter liteloaderOnTickAdapter;
    private LiteLoaderOnRenderAdapter liteLoaderOnRenderAdapter;
    private LiteLoaderOnConnectAdapter liteLoaderOnConnectAdapter;

    private LiteLoaderCommandRegister liteloaderCommandRegister;

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
        liteLoaderOnRenderAdapter.trigger(minecraft, partialTicks, inGame, clock);
    }

    @Override
    public String getVersion() {
        return HypixelUtilsCore.VERSION;
    }

    @Override
    public void init(File configFile) {
        liteloaderCommandRegister = new LiteLoaderCommandRegister();
        HypixelUtilsCore.commandRegister = liteloaderCommandRegister;

        HypixelUtilsCore.initCloudConfig(Paths.get(configFile.getAbsolutePath(), "hypixelutils-configkey.txt"));
        CloudConfigManager config = new CloudConfigManager(HypixelUtilsCore.CONFIG_SERVER, HypixelUtilsCore.getSavedConfigKey());
        config.load();

        HypixelUtilsCore.config = config;
        HypixelUtilsCore.scheduler = new Scheduler();
        HypixelUtilsCore.eventManager = new EventManager();

        HypixelUtilsCore.init();

        liteloaderOnBungeeServerChangeAdapter = new LiteLoaderOnBungeeServerChangeAdapter(HypixelUtilsCore.eventManager);
        liteloaderOnChatAdapter = new LiteLoaderOnChatAdapter(HypixelUtilsCore.eventManager);
        liteloaderOnTickAdapter = new LiteLoaderOnTickAdapter(HypixelUtilsCore.eventManager);
        liteLoaderOnRenderAdapter = new LiteLoaderOnRenderAdapter(HypixelUtilsCore.eventManager);
        liteLoaderOnConnectAdapter = new LiteLoaderOnConnectAdapter(HypixelUtilsCore.eventManager);
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath) {

    }

    @Override
    public String getName() {
        return HypixelUtilsCore.MODID;
    }

    @Override
    public void onJoinGame(INetHandler netHandler, S01PacketJoinGame joinGamePacket, ServerData serverData, RealmsServer realmsServer) {
        liteLoaderOnConnectAdapter.trigger(serverData);
    }

    @Override
    public List<Class<? extends Packet>> getHandledPackets() {
        List handledPackets = new ArrayList<Class<? extends Packet>>();
        handledPackets.add(S07PacketRespawn.class);
        return handledPackets;
    }

    @Override
    public boolean handlePacket(INetHandler netHandler, Packet packet) {
        if(packet instanceof S07PacketRespawn)
            return onRespawn(netHandler, (S07PacketRespawn)packet);
        return true;
    }

    private boolean onRespawn(INetHandler netHandler, S07PacketRespawn packet) {
        WorldDimension dimension = WorldDimension.fromDimensionId(packet.func_149082_c());
        return liteloaderOnBungeeServerChangeAdapter.onRespawn(dimension);
    }
}
