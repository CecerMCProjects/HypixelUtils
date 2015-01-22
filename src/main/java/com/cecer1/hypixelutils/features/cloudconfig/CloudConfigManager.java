package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.config.IConfigManager;
import com.cecer1.hypixelutils.gui.GuiConfigManagerWrapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.HttpResponse;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.async.Callback;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.exceptions.UnirestException;

import java.util.UUID;
import java.util.concurrent.Future;

/**
 * This class is only for testing. It does not save values nor are command aliases changeable.
 */
public class CloudConfigManager implements IConfigManager {

    private String _configServerPrefix;
    private UUID _configKey;

    private boolean _isLobbyProtectionDisabled;
    private boolean _isFilterGuildChatsEnabled;
    private boolean _isFilterPartChatEnabled;
    private boolean _isInstantBedEnabled;
    private boolean _isPartyAutoRemoveOfflineEnabled;
    private boolean _isDebugModeEnabled;

    public CloudConfigManager(String configServerPrefix, UUID configKey) {
        _configServerPrefix = configServerPrefix;
        _configKey = configKey;
    }
    public UUID getConfigKey() {
        return _configKey;
    }
    public void setConfigKey(UUID configKey) {
        _configKey = configKey;
    }

    private boolean _disableSaving = false; // Used to prevent saving while loading (as loading triggers saving for each value).

    public Future<HttpResponse<String>> load() {
        if(this.isDebugModeEnabled()) {
            ChatOutputs.printDebugLoadingCloudConfig(_configKey);
        }

        _disableSaving = true;
        final CloudConfigManager configManager = this;

        return CloudConfigServerGateway.getConfigJsonStringFromServer(_configServerPrefix, _configKey.toString(), new Callback<String>() {
            @Override
            public void completed(HttpResponse<String> httpResponse) {
                try {
                    CloudConfigServerGateway.applyJsonConfig(configManager, httpResponse.getBody());

                    if(HypixelUtilsCore.config instanceof GuiConfigManagerWrapper)
                        ((GuiConfigManagerWrapper)HypixelUtilsCore.config).loadBackingValues();

                    if(configManager.isDebugModeEnabled()) {
                        ChatOutputs.printDebugLoadedCloudConfig(_configKey);
                    }

                    
                } catch (CloudConfigServerException e) {
                    e.printToChat("Failed to load config from server!");
                } finally {
                    _disableSaving = false;
                }
            }

            @Override
            public void failed(UnirestException e) {
                e.printStackTrace();
                new CloudConfigServerException("UnirestException thrown while getting the request from the server")
                        .printToChat("Failed to load config from server!");
                _disableSaving = false;
            }

            @Override
            public void cancelled() {
                new CloudConfigServerException("Unirest Request was cancelled!")
                        .printToChat("Failed to load config from server!");
                _disableSaving = false;
            }
        });
    }

    public Future<HttpResponse<String>> save() {
        if(_disableSaving)
            return null;

        if(this.isDebugModeEnabled()) {
            ChatOutputs.printDebugSavingCloudConfig(_configKey);
        }

        final CloudConfigManager configManager = this;
        String jsonString = CloudConfigServerGateway.getConfigStringFromConfigManager(this);
        return CloudConfigServerGateway.storeJsonConfig(_configServerPrefix, _configKey.toString(), jsonString, new Callback<String>() {
            @Override
            public void completed(HttpResponse<String> httpResponse) {
                try {
                    if (configManager.isDebugModeEnabled()) {
                        ChatOutputs.printDebugLoadedCloudConfig(_configKey);
                    }
                } finally {
                    _disableSaving = false;
                }
            }

            @Override
            public void failed(UnirestException e) {
                e.printStackTrace();
                new CloudConfigServerException("UnirestException thrown while getting the request from the server")
                        .printToChat("Failed to save config to server!");
                _disableSaving = false;
            }

            @Override
            public void cancelled() {
                new CloudConfigServerException("Unirest Request was cancelled!")
                        .printToChat("Failed to save config to server!");
                _disableSaving = false;
            }
        });
    }

    @Override
    public boolean isBypassLobbyProtectionEnabled() {
        return _isLobbyProtectionDisabled;
    }

    @Override
    public IConfigManager setBypassLobbyProtectionEnabled(boolean disabled) {
        _isLobbyProtectionDisabled = disabled;
        save();
        return this;
    }

    @Override
    public boolean isFilterGuildChatEnabled() {
        return _isFilterGuildChatsEnabled;
    }

    @Override
    public IConfigManager setFilterGuildChatEnabled(boolean enabled) {
        _isFilterGuildChatsEnabled = enabled;
        save();
        return this;
    }

    @Override
    public boolean isFilterPartyChatEnabled() {
        return _isFilterPartChatEnabled;
    }

    @Override
    public IConfigManager setFilterPartyChatEnabled(boolean enabled) {
        _isFilterPartChatEnabled = enabled;
        save();
        return this;
    }

    @Override
    public boolean isInstantBedEnabled() {
        return _isInstantBedEnabled;
    }

    @Override
    public IConfigManager setInstantBedEnabled(boolean enabled) {
        _isInstantBedEnabled = enabled;
        save();
        return this;
    }

    @Override
    public boolean isPartyAutoRemoveOfflineEnabled() {
        return _isPartyAutoRemoveOfflineEnabled;
    }

    @Override
    public IConfigManager setPartyAutoRemoveOfflineEnabled(boolean enabled) {
        _isPartyAutoRemoveOfflineEnabled = enabled;
        save();
        return this;
    }

    @Override
    public boolean isDebugModeEnabled() {
        return _isDebugModeEnabled;
    }

    @Override
    public IConfigManager setDebugModeEnabled(boolean enabled) {
        ChatOutputs.debugMode = enabled;
        _isDebugModeEnabled = enabled;
        save();
        return this;
    }

    @Override
    public String[] getGuildChatToggleCommands() {
        return new String[] {"guildchattoggle", "gchattoggle", "gtoggle"};
    }

    @Override
    public String[] getImprovedLobbyCommands() {
        return new String[] {"ilobby", "lobby", "hub", "main", "leave"};
    }

    @Override
    public String[] getInstantBedCommands() {
        return new String[] {"instantbed"};
    }

    @Override
    public String[] getLobbyProtectionCommands() {
        return new String[] {"lobbyprotectiontoggle", "lobbylobbytoggle", "lobbyprotection", "lobbylobby"};
    }

    @Override
    public String[] getPartyAutoRemoveCommands() {
        return new String[] {"partyautoremovetoggle", "pautoremovetoggle", "partyautoremoveofflinetoggle", "pautoremoveofflinetoggle", "partyautoremove", "pautoremove", "partyautoremoveoffline", "pautoremoveoffline"};
    }

    @Override
    public String[] getPartyChatToggleCommands() {
        return new String[] {"partychattoggle", "pchattoggle", "ptoggle"};
    }

    @Override
    public String[] getTipAndThankCommands() {
        return new String[] {"tipandthank"};
    }
    @Override
    public String[] getRageQuitCommands() {
        return new String[] {"ragequit"};
    }

    @Override
    public String[] getLobbyTypes() {
        return new String[] {"arcade", "arena", "blitz", "cops", "main", "megawalls", "paintball", "quake", "tnt", "uhc", "vampirez", "walls", "xmas"};
    }

    public Future<HttpResponse<String>> deleteRemoteConfig() {
        return CloudConfigServerGateway.deleteConfig(_configServerPrefix, _configKey.toString(), new Callback<String>() {
            @Override
            public void completed(HttpResponse<String> httpResponse) {
                JsonObject jsonConfig = new JsonParser().parse(httpResponse.getBody()).getAsJsonObject();
                if(!jsonConfig.get("success").getAsBoolean()) {
                    new CloudConfigServerException(jsonConfig.get("cause").getAsString())
                            .printToChat("Failed to remove old config key!");
                }
            }

            @Override
            public void failed(UnirestException e) {
                e.printStackTrace();
                new CloudConfigServerException("UnirestException thrown while getting the request from the server")
                        .printToChat("Failed to remove old config key!");
            }

            @Override
            public void cancelled() {
                new CloudConfigServerException("Unirest Request was cancelled!")
                        .printToChat("Failed to remove old config key!");
            }
        });
    }
}
