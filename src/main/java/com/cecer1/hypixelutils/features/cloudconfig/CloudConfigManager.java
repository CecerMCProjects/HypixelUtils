package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.config.IConfigManager;
import com.cecer1.hypixelutils.gui.GuiConfigManagerWrapper;

import java.util.UUID;

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

    public void load() {
        if(this.isDebugModeEnabled()) {
            ChatOutputs.printDebugLoadingCloudConfig(_configKey);
        }

        _disableSaving = true;
        String jsonString = null;
        try {
            jsonString = CloudConfigServerGateway.getConfigJsonStringFromServer(_configServerPrefix, _configKey.toString());
            CloudConfigServerGateway.applyJsonConfig(this, jsonString);
            if(HypixelUtilsCore.config instanceof GuiConfigManagerWrapper)
                ((GuiConfigManagerWrapper)HypixelUtilsCore.config).loadBackingValues();

            if(this.isDebugModeEnabled()) {
                ChatOutputs.printDebugLoadedCloudConfig(_configKey);
            }
        } catch (CloudConfigServerException e) {
            e.printToChat("Failed to load config from server!");
        } finally {
            _disableSaving = false;
        }
    }

    public void save() {
        if(_disableSaving)
            return;

        if(this.isDebugModeEnabled()) {
            ChatOutputs.printDebugSavingCloudConfig(_configKey);
        }

        String jsonString = CloudConfigServerGateway.getConfigStringFromConfigManager(this);
        try {
            CloudConfigServerGateway.storeJsonConfig(_configServerPrefix, _configKey.toString(), jsonString);

            if(this.isDebugModeEnabled()) {
                ChatOutputs.printDebugSavedCloudConfig(_configKey);
            }
        } catch (CloudConfigServerException e) {
            e.printToChat("Failed to save config to server!");
        }
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

    public void deleteRemoteConfig() {
        try {
            CloudConfigServerGateway.deleteConfig(_configServerPrefix, _configKey.toString());
        } catch (CloudConfigServerException e) {
            e.printToChat("Failed to remove old config key!");
        }
    }
}
