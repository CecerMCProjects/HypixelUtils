package com.cecer1.hypixelutils.events;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.events.adapters.LiteLoaderOnBungeeServerChangeAdapter;
import com.cecer1.hypixelutils.events.eventdata.*;
import com.cecer1.hypixelutils.utils.WorldDimension;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.PlayerInteractionListener;
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
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;

import java.util.ArrayList;
import java.util.List;

public class LiteLoaderEventForwarder {
    private LiteLoaderOnBungeeServerChangeAdapter _bungeeServerChangeAdapter;
    
    public LiteLoaderEventForwarder() {
        _bungeeServerChangeAdapter = new LiteLoaderOnBungeeServerChangeAdapter(HypixelUtilsCore.eventManager);
    }
    
    public boolean onChat(IChatComponent chat, String message, LiteLoaderEventBroker.ReturnValue<IChatComponent> newMessage) {
        OnChatEventData eventData = new OnChatEventData(chat);
        HypixelUtilsCore.eventManager.fireEvent(eventData);

        return !eventData.isCanceled();
    }


    public void onPreRenderChat(int screenWidth, int screenHeight, GuiNewChat chat) {
    }


    public void onPostRenderChat(int screenWidth, int screenHeight, GuiNewChat chat) {
    }


    public void onRenderEntity(Render render, Entity entity, double xPos, double yPos, double zPos, float yaw, float partialTicks) {

    }


    public void onPostRenderEntity(Render render, Entity entity, double xPos, double yPos, double zPos, float yaw, float partialTicks) {

    }


    public void onRunGameLoop(Minecraft minecraft) {

    }


    public void onPreRenderHUD(int screenWidth, int screenHeight) {
    }


    public void onPostRenderHUD(int screenWidth, int screenHeight) {
        OnPostRenderHUDEventData eventData = new OnPostRenderHUDEventData(screenWidth, screenHeight);
        HypixelUtilsCore.eventManager.fireEvent(eventData);
    }


    public void onJoinGame(INetHandler netHandler, S01PacketJoinGame joinGamePacket, ServerData serverData, RealmsServer realmsServer) {
        OnJoinGameEventData eventData = new OnJoinGameEventData(serverData);
        HypixelUtilsCore.eventManager.fireEvent(eventData);
    }


    public boolean onSendChatMessage(String message) {
        if(HypixelUtilsCore.skipEventProcessingChatMessages.contains(message)) {
            HypixelUtilsCore.skipEventProcessingChatMessages.remove(message);
            return true;
        }
        return HypixelUtilsCore.commandRegister.trigger(message);
    }

    public void onPlayerClickedAir(EntityPlayerMP player, PlayerInteractionListener.MouseButton button, BlockPos tracePos, EnumFacing traceSideHit, MovingObjectPosition.MovingObjectType traceHitType) {

    }


    public boolean onPlayerClickedBlock(EntityPlayerMP player, PlayerInteractionListener.MouseButton button, BlockPos hitPos, EnumFacing sideHit) {
        return true;
    }


    public boolean onPlayerMove(EntityPlayerMP playerMP, Position from, Position to, LiteLoaderEventBroker.ReturnValue<Position> newPos) {
        return true;
    }


    public void onPostLogin(INetHandlerLoginClient netHandler, S02PacketLoginSuccess packet) {

    }


    public void onPostRenderEntities(float partialTicks) {

    }


    public void onPostRender(float partialTicks) {

    }


    public boolean onPreJoinGame(INetHandler netHandler, S01PacketJoinGame joinGamePacket) {
        return true;
    }


    public void onRender() {
        OnRenderEventData eventData = new OnRenderEventData();
        HypixelUtilsCore.eventManager.fireEvent(eventData);
    }


    public void onRenderGui(GuiScreen currentScreen) {
    }


    public void onRenderWorld() {
    }


    public void onSetupCameraTransform() {

    }


    public boolean onSaveScreenshot(String screenshotName, int width, int height, Framebuffer fbo, LiteLoaderEventBroker.ReturnValue<IChatComponent> message) {
        return true;
    }


    public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock) {
        if(!clock)
            return;

        OnTickEventData eventData = new OnTickEventData();
        HypixelUtilsCore.eventManager.fireEvent(eventData);
    }


    public void onViewportResized(ScaledResolution resolution, int displayWidth, int displayHeight) {

    }


    public void onFullScreenToggled(boolean fullScreen) {

    }
    


    public List<Class<? extends Packet>> getHandledPackets() {
        List handledPackets = new ArrayList<Class<? extends Packet>>();
        handledPackets.add(S07PacketRespawn.class);
        return handledPackets;
    }


    public boolean handlePacket(INetHandler netHandler, Packet packet) {
        if(packet instanceof S07PacketRespawn)
            return onRespawn(netHandler, (S07PacketRespawn)packet);
        return true;
    }

    public boolean onRespawn(INetHandler netHandler, S07PacketRespawn packet) {
        WorldDimension dimension = WorldDimension.fromDimensionId(packet.func_149082_c());
        return _bungeeServerChangeAdapter.onRespawn(dimension);
    }
}
