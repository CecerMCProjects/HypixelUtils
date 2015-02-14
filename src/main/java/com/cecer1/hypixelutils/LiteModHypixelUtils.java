package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.clientcommands.LiteLoaderCommandRegister;
import com.cecer1.hypixelutils.events.EventManager;
import com.cecer1.hypixelutils.events.LiteLoaderEventForwarder;
import com.cecer1.hypixelutils.utils.MD5Checksum;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.*;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.core.LiteLoaderEventBroker;
import com.mumfrey.liteloader.util.Position;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.login.INetHandlerLoginClient;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;

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
import java.util.List;

public class LiteModHypixelUtils implements InitCompleteListener, GameLoopListener, ChatRenderListener, PostLoginListener, EntityRenderListener, Tickable, HUDRenderListener, PostRenderListener, ChatFilter, PacketHandler, JoinGameListener, RenderListener, OutboundChatFilter, PreJoinGameListener, ViewportListener, PlayerInteractionListener, ScreenshotListener, PlayerMoveListener {
    public static LiteModHypixelUtils instance;

    private LiteLoaderEventForwarder _forwarder;
    public LiteModHypixelUtils() {
        instance = this;
        HypixelUtilsCore.eventManager = new EventManager();
        HypixelUtilsCore.commandRegister = new LiteLoaderCommandRegister();
        _forwarder = new LiteLoaderEventForwarder();
    }

    @Override
    public String getName() {
        return HypixelUtilsCore.MODID;
    }
    @Override
    public String getVersion() {
        return HypixelUtilsCore.VERSION;
    }
    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath) {

    }
    @Override
    public void init(File configFile) {
        try {
            prepareLibs();
            
            HypixelUtilsCore.scheduler = new Scheduler();
            HypixelUtilsCore.initCloudConfig(Paths.get(configFile.getAbsolutePath(), "hypixelutils-configkey.txt"));
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onInitCompleted(Minecraft minecraft, LiteLoader loader) {
        try {
            HypixelUtilsCore.init();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private void prepareLibs() {
        Path libsDirPath = Paths.get(LiteLoader.getModsFolder().getAbsolutePath(), "libs");
        File libsDirFile = libsDirPath.toFile();
        if (!libsDirFile.exists()) {
            libsDirFile.mkdirs();
        } else {
            if (!libsDirFile.isDirectory()) {
                throw new RuntimeException(new IOException("Unable to create /mobs/libs directory. Conflicting file name! Refusing to clobber!"));
            }
        }

        File libFile;

        libFile = Paths.get(libsDirPath.toString(), "hypixelutils-unirest.jar").toFile();
        ensureLib(libFile, "f835f52940a14123da516c21367be7cf", "http://www.cecer1.com/hypixelutils/libs/hypixelutils-unirest.jar");
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
    public boolean onChat(IChatComponent chat, String message, LiteLoaderEventBroker.ReturnValue<IChatComponent> newMessage) {
        return _forwarder.onChat(chat, message, newMessage);
    }

    @Override
    public void onPreRenderChat(int screenWidth, int screenHeight, GuiNewChat chat) {
        _forwarder.onPreRenderChat(screenWidth, screenHeight, chat);
    }

    @Override
    public void onPostRenderChat(int screenWidth, int screenHeight, GuiNewChat chat) {
        _forwarder.onPostRenderChat(screenWidth, screenHeight, chat);
    }

    @Override
    public void onRenderEntity(Render render, Entity entity, double xPos, double yPos, double zPos, float yaw, float partialTicks) {
        _forwarder.onRenderEntity(render, entity, xPos, yPos, zPos, yaw, partialTicks);
    }

    @Override
    public void onPostRenderEntity(Render render, Entity entity, double xPos, double yPos, double zPos, float yaw, float partialTicks) {
        _forwarder.onPostRenderEntity(render, entity, xPos, yPos, zPos, yaw, partialTicks);
    }

    @Override
    public void onRunGameLoop(Minecraft minecraft) {
        _forwarder.onRunGameLoop(minecraft);
    }

    @Override
    public void onPreRenderHUD(int screenWidth, int screenHeight) {
        _forwarder.onPreRenderHUD(screenWidth, screenHeight);
    }

    @Override
    public void onPostRenderHUD(int screenWidth, int screenHeight) {
        _forwarder.onPostRenderHUD(screenWidth, screenHeight);
    }

    @Override
    public void onJoinGame(INetHandler netHandler, S01PacketJoinGame joinGamePacket, ServerData serverData, RealmsServer realmsServer) {
        _forwarder.onJoinGame(netHandler, joinGamePacket, serverData, realmsServer);
    }

    @Override
    public boolean onSendChatMessage(String message) {
        return _forwarder.onSendChatMessage(message);
    }

    @Override
    public List<Class<? extends Packet>> getHandledPackets() {
        return _forwarder.getHandledPackets();
    }

    @Override
    public boolean handlePacket(INetHandler netHandler, Packet packet) {
        return _forwarder.handlePacket(netHandler, packet);
    }

    @Override
    public void onPlayerClickedAir(EntityPlayerMP player, MouseButton button, BlockPos tracePos, EnumFacing traceSideHit, MovingObjectPosition.MovingObjectType traceHitType) {
        _forwarder.onPlayerClickedAir(player, button, tracePos, traceSideHit, traceHitType);
    }

    @Override
    public boolean onPlayerClickedBlock(EntityPlayerMP player, MouseButton button, BlockPos hitPos, EnumFacing sideHit) {
        return _forwarder.onPlayerClickedBlock(player, button, hitPos, sideHit);
    }

    @Override
    public boolean onPlayerMove(EntityPlayerMP playerMP, Position from, Position to, LiteLoaderEventBroker.ReturnValue<Position> newPos) {
        return _forwarder.onPlayerMove(playerMP, from, to, newPos);
    }

    @Override
    public void onPostLogin(INetHandlerLoginClient netHandler, S02PacketLoginSuccess packet) {
        _forwarder.onPostLogin(netHandler, packet);
    }

    @Override
    public void onPostRenderEntities(float partialTicks) {
        _forwarder.onPostRenderEntities(partialTicks);
    }

    @Override
    public void onPostRender(float partialTicks) {
        _forwarder.onPostRender(partialTicks);
    }

    @Override
    public boolean onPreJoinGame(INetHandler netHandler, S01PacketJoinGame joinGamePacket) {
        return _forwarder.onPreJoinGame(netHandler, joinGamePacket);
    }

    @Override
    public void onRender() {
        _forwarder.onRender();
    }

    @Override
    public void onRenderGui(GuiScreen currentScreen) {
        _forwarder.onRenderGui(currentScreen);
    }

    @Override
    public void onRenderWorld() {
        _forwarder.onRenderWorld();
    }

    @Override
    public void onSetupCameraTransform() {
        _forwarder.onSetupCameraTransform();
    }

    @Override
    public boolean onSaveScreenshot(String screenshotName, int width, int height, Framebuffer fbo, LiteLoaderEventBroker.ReturnValue<IChatComponent> message) {
        return _forwarder.onSaveScreenshot(screenshotName, width, height, fbo, message);
    }

    @Override
    public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
        _forwarder.onTick(minecraft, partialTicks, inGame, clock);
    }

    @Override
    public void onViewportResized(ScaledResolution resolution, int displayWidth, int displayHeight) {
        _forwarder.onViewportResized(resolution, displayWidth, displayHeight);
    }

    @Override
    public void onFullScreenToggled(boolean fullScreen) {
        _forwarder.onFullScreenToggled(fullScreen);
    }
}
