package com.cecer1.hypixelutils.config;

/**
 * This class is only for testing. It does not save values nor are command aliases changeable.
 */
public class DummyConfigManager implements IConfigManager {
    private boolean _isLobbyProtectionDisabled;
    private boolean _iFilterGuildChatsEnabled;
    private boolean _isFilterPartChatEnabled;
    private boolean _isInstantBedEnabled;
    private boolean _isPartyAutoRemoveOfflineEnabled;
    private boolean _isDebugModeEnabled;

    @Override
    public boolean isLobbyProtectionEnabled() {
        return _isLobbyProtectionDisabled;
    }

    @Override
    public IConfigManager setLobbyProtectionEnabled(boolean disabled) {
        _isLobbyProtectionDisabled = disabled;
        return this;
    }

    @Override
    public boolean isFilterGuildChatEnabled() {
        return _iFilterGuildChatsEnabled;
    }

    @Override
    public IConfigManager setFilterGuildChatEnabled(boolean enabled) {
        _iFilterGuildChatsEnabled = enabled;
        return this;
    }

    @Override
    public boolean isFilterPartyChatEnabled() {
        return _isFilterPartChatEnabled;
    }

    @Override
    public IConfigManager setFilterPartyChatEnabled(boolean enabled) {
        _isFilterPartChatEnabled = enabled;
        return this;
    }

    @Override
    public boolean isInstantBedEnabled() {
        return _isInstantBedEnabled;
    }

    @Override
    public IConfigManager setInstantBedEnabled(boolean enabled) {
        _isInstantBedEnabled = enabled;
        return this;
    }

    @Override
    public boolean isPartyAutoRemoveOfflineEnabled() {
        return _isPartyAutoRemoveOfflineEnabled;
    }

    @Override
    public IConfigManager setPartyAutoRemoveOfflineEnabled(boolean enabled) {
        _isPartyAutoRemoveOfflineEnabled = enabled;
        return this;
    }

    @Override
    public boolean isDebugModeEnabled() {
        return _isDebugModeEnabled;
    }

    @Override
    public IConfigManager setDebugModeEnabled(boolean enabled) {
        _isDebugModeEnabled = enabled;
        return this;
    }

    @Override
    public String[] getGuildChatToggleCommands() {
        return new String[] {"guildchattoggle", "gchattoggle", "gtoggle"};
    }

    @Override
    public String[] getImprovedLobbyCommands() {
        return new String[] {"ilobby"};
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
        return new String[] {"partyautoremovetoggle", "pautoremovetoggle"};
    }

    @Override
    public String[] getPartyChatToggleCommands() {
        return new String[] {"partychattoggle", "pchattoggle", "ptoggle"};
    }
}
