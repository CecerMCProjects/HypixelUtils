package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.commands.*;
import com.cecer1.hypixelutils.features.general.HypixelStateUpdateType;
import com.cecer1.hypixelutils.features.general.OnHypixelStateUpdatedEventData;
import com.cecer1.modframework.common.events.IOnBungeeServerChangeEventHandler;
import com.cecer1.modframework.common.events.IOnConnectEventHandler;
import net.minecraft.client.Minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HypixelState implements IOnBungeeServerChangeEventHandler, IOnConnectEventHandler {
    private static final Pattern _lobbyServerNamePattern = Pattern.compile("^(.*)lobby(\\d+)$");

    public boolean isConnected() {
        String currentServer = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        if(currentServer.endsWith(".hypixel.net"))
            return true;
        if(currentServer.contains(".hypixel.net:"))
            return true;
        return false;
    }

    private String _currentServerName;
    public String getCurrentServerName() {
        return _currentServerName;
    }
    public void setCurrentServerName(String currentServerName) {
        _currentServerName = currentServerName;
        HypixelUtilsCore.eventManager.fireEvent(new OnHypixelStateUpdatedEventData(HypixelStateUpdateType.SERVER_NAME));
    }
    public void updateServerName() {
        HypixelUtilsCore.commandJobManager.queueJob(new HypixelCommandJobWhereami(new IHypixelCommandCallbackWhereami() {
            @Override
            public void result(String serverName) {
                setCurrentServerName(serverName);

                if(serverName.equals("limbo")) {
                    setCurrentMapName("Limbo");
                    return;
                }

                Matcher lobbyServerNameMatcher = _lobbyServerNamePattern.matcher(serverName);
                if(lobbyServerNameMatcher.matches()) {
                    String lobbyType = lobbyServerNameMatcher.group(1);
                    String lobbyNumber = lobbyServerNameMatcher.group(2);

                    if(lobbyType.equals(""))
                        setCurrentMapName("Main Lobby");
                    else if(lobbyType.equals("adventure"))
                        setCurrentMapName("Adventure Lobby");
                    else  if(lobbyType.equals("arcade"))
                        setCurrentMapName("Arcade Lobby");
                    else  if(lobbyType.equals("arena"))
                        setCurrentMapName("Arena Lobby");
                    else  if(lobbyType.equals("blitz"))
                        setCurrentMapName("Blitz Lobby");
                    else  if(lobbyType.equals("paintball"))
                        setCurrentMapName("Paintball Lobby");
                    else  if(lobbyType.equals("vampirez"))
                        setCurrentMapName("VampireZ Lobby");
                    else  if(lobbyType.equals("mcgo"))
                        setCurrentMapName("Cops and Crims Lobby");
                    else  if(lobbyType.equals("tnt"))
                        setCurrentMapName("TNT Games Lobby");
                    else  if(lobbyType.equals("quake"))
                        setCurrentMapName("Quakecraft Lobby");
                    else  if(lobbyType.equals("uhc"))
                        setCurrentMapName("UHC Champions Lobby");
                    else  if(lobbyType.equals("walls"))
                        setCurrentMapName("Walls Lobby");
                    else  if(lobbyType.equals("megawalls"))
                        setCurrentMapName("MegaWalls Lobby");
                    else
                        setCurrentMapName("Unknown Lobby");
                }
            }
        }));
    }

    private String _currentMapName;
    public String getCurrentMapName() {
        return _currentMapName;
    }
    public void setCurrentMapName(String currentMapName) {
        _currentMapName = currentMapName;
        HypixelUtilsCore.eventManager.fireEvent(new OnHypixelStateUpdatedEventData(HypixelStateUpdateType.MAP_NAME));
    }
    public void updateMapName() {
        HypixelUtilsCore.commandJobManager.queueJob(new HypixelCommandJobWtfmap(new IHypixelCommandCallbackWtfmap() {
            @Override
            public void result(String mapName) {
                if(mapName != null)
                    setCurrentMapName(mapName);
            }
        }));
    }

    private String _currentProxyName;
    public String getCurrentProxyName() {
        return _currentProxyName;
    }
    public void setCurrentProxyName(String currentProxyName) {
        _currentProxyName = currentProxyName;
        HypixelUtilsCore.eventManager.fireEvent(new OnHypixelStateUpdatedEventData(HypixelStateUpdateType.PROXY_NAME));
    }
    public void updateProxyName() {
        HypixelUtilsCore.commandJobManager.queueJob(new HypixelCommandJobProxy(new IHypixelCommandCallbackProxy() {
            @Override
            public void result(String proxyName) {
                setCurrentProxyName(proxyName);
            }
        }));
    }

    @Override
    public void onBungeeServerChange(IOnBungeeServerChangeEventHandler.IOnBungeeServerChangeEventData eventData) {
        updateServerName();
        updateMapName();
    }

    @Override
    public void onConnect(IOnConnectEventData event) {
        if(isConnected()) {
            updateProxyName();
            updateServerName();
            updateMapName();
        }
    }
}
