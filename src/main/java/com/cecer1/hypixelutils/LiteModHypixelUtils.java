package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.features.cloudconfig.CloudConfigManager;
import com.cecer1.hypixelutils.gui.GuiConfigManagerWrapper;
import com.cecer1.modframework.common.Scheduler;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.common.utils.MD5Checksum;
import com.cecer1.modframework.common.utils.WorldDimension;
import com.cecer1.modframework.liteloader.commands.LiteLoaderCommandRegister;
import com.cecer1.modframework.liteloader.events.adapters.*;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.*;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.util.IChatComponent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        if(HypixelUtilsCore.skipEventProcessingChatMessages.contains(message)) {
            HypixelUtilsCore.skipEventProcessingChatMessages.remove(message);
            return true;
        }
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
        prepareLibs();
        
        liteloaderCommandRegister = new LiteLoaderCommandRegister();
        HypixelUtilsCore.commandRegister = liteloaderCommandRegister;

        HypixelUtilsCore.initCloudConfig(Paths.get(configFile.getAbsolutePath(), "hypixelutils-configkey.txt"));
        CloudConfigManager config = new CloudConfigManager(HypixelUtilsCore.CONFIG_SERVER, HypixelUtilsCore.getSavedConfigKey());
        try {
            config.load().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        HypixelUtilsCore.config = config;
        HypixelUtilsCore.scheduler = new Scheduler();
        HypixelUtilsCore.eventManager = new EventManager();

        HypixelUtilsCore.init();

        // Switch out the normal config for the wrapped one now that we have called HypixelUtilsCore.init() and the GUI system is ready.
        GuiConfigManagerWrapper configGuiWrapper = new GuiConfigManagerWrapper(config);
        HypixelUtilsCore.config = configGuiWrapper;
                
        liteloaderOnBungeeServerChangeAdapter = new LiteLoaderOnBungeeServerChangeAdapter(HypixelUtilsCore.eventManager);
        liteloaderOnChatAdapter = new LiteLoaderOnChatAdapter(HypixelUtilsCore.eventManager);
        liteloaderOnTickAdapter = new LiteLoaderOnTickAdapter(HypixelUtilsCore.eventManager);
        liteLoaderOnRenderAdapter = new LiteLoaderOnRenderAdapter(HypixelUtilsCore.eventManager);
        liteLoaderOnConnectAdapter = new LiteLoaderOnConnectAdapter(HypixelUtilsCore.eventManager);
    }


    private void prepareLibs() {
        Path libsDirPath = Paths.get(LiteLoader.getModsFolder().getAbsolutePath(), "libs");
        File libsDirFile = libsDirPath.toFile();
        if(!libsDirFile.exists()) {
            libsDirFile.mkdirs();
        } else {
            if(!libsDirFile.isDirectory()) {
                throw new RuntimeException(new IOException("Unable to create /mobs/libs directory. Conflicting file name! Refusing to clobber!"));
            }
        }
        
        File libFile;
        
        libFile = Paths.get(libsDirPath.toString(), "httpasyncclient-4.0.2.jar").toFile();
        ensureLib(libFile, "3c0145d6ab072b8e755699c246b224a3", "http://www.cecer1.com/hypixelutils/libs/httpasyncclient-4.0.2.jar");

        libFile = Paths.get(libsDirPath.toString(), "httpclient-4.3.6.jar").toFile();
        ensureLib(libFile, "98ac3eaa1d681c3adc962d756ebed5a0", "http://www.cecer1.com/hypixelutils/libs/httpclient-4.3.6.jar");

        libFile = Paths.get(libsDirPath.toString(), "httpcore-nio-4.3.2.jar").toFile();
        ensureLib(libFile, "2523ba09110e521a02c54e271c06a93d", "http://www.cecer1.com/hypixelutils/libs/httpcore-nio-4.3.2.jar");

        libFile = Paths.get(libsDirPath.toString(), "httpmime-4.3.6.jar").toFile();
        ensureLib(libFile, "ec661ad97a37cca0a91186625cf1fcba", "http://www.cecer1.com/hypixelutils/libs/httpmime-4.3.6.jar");

        libFile = Paths.get(libsDirPath.toString(), "json-20090211.jar").toFile();
        ensureLib(libFile, "333139fffc6c9d4bc3d2495d9613f092", "http://www.cecer1.com/hypixelutils/libs/json-20090211.jar");

        libFile = Paths.get(libsDirPath.toString(), "unirest-java-1.3.27.jar").toFile();
        ensureLib(libFile, "ec4cdf09634f33c15d7c2972a83cd286", "http://www.cecer1.com/hypixelutils/libs/unirest-java-1.3.27.jar");
    }
    
    private void ensureLib(File file, String expectedChecksum, String downloadUrl) {
        boolean fileExisted = file.exists();
        if(!fileExisted) {
            try {
                System.out.println(file.getAbsolutePath() + " does not exist. Downloading from " + downloadUrl);
                URL website = new URL(downloadUrl);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream(file);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                System.out.println("Downloaded finished. Comparing checksum.");
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            if(MD5Checksum.compareMD5Checksum(file.getAbsolutePath(), expectedChecksum)) {
                System.out.println("Successful checksum for " + file.getAbsolutePath() + " - Expected and calculated " + expectedChecksum);
            } else {
                System.out.println("Failed checksum for " + file.getAbsolutePath() + " - Expected "+ expectedChecksum + " but calculated " + expectedChecksum);
                if(fileExisted) {
                    throw new RuntimeException(new IOException("Checksum mismatch on " + file.getAbsolutePath() + " - Corrupt? - Refusing to clobber! Please manually resolve this issue."));
                } else {
                    file.delete();
                    throw new RuntimeException(new IOException("Checksum mismatch on downloaded " + file.getAbsolutePath() + " - Corrupt? - File deleted. Restart the game to retry."));
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to calculate checksum for " + file.getAbsolutePath() + " - WARNING: The file may be corrupt but HypixelUtils will assume it is fine!");
        }

        try {
            LiteLoader.getClassLoader().addURL(file.toURI().toURL());
            System.out.println("Added " + file.toURI().toURL() + " to LiteLoader ClassLoader.");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
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
