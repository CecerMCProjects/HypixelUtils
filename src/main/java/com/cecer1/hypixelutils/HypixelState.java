package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.commands.*;
import com.cecer1.hypixelutils.features.general.HypixelStateUpdateType;
import com.cecer1.hypixelutils.features.general.OnHypixelStateUpdatedEventData;
import com.cecer1.modframework.common.events.IOnBungeeServerChangeEventHandler;
import net.minecraft.client.Minecraft;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HypixelState implements IOnBungeeServerChangeEventHandler {
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
                        setCurrentMapName("");
                    else  if(lobbyType.equals("arcade"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("arena"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("blitz"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("paintball"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("vampirez"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("mcgo"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("tnt"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("quake"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("uhc"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("walls"))
                        setCurrentMapName("");
                    else  if(lobbyType.equals("megawalls"))
                        setCurrentMapName("");
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
                setCurrentMapName(mapName);
            }
        }));
    }

    private String currentProxyName;
    public String getCurrentProxyName() {
        return currentProxyName;
    }
    public void setCurrentProxyName(String currentProxyName) {
        currentProxyName = currentProxyName;
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
        getCurrentServerName();
        getCurrentMapName();
    }
}
