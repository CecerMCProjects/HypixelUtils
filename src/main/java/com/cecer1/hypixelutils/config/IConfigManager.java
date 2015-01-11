package com.cecer1.hypixelutils.config;

public interface IConfigManager {
    public boolean isBypassLobbyProtectionEnabled();
    public IConfigManager setBypassLobbyProtectionEnabled(boolean enabled);

    public boolean isFilterGuildChatEnabled();
    public IConfigManager setFilterGuildChatEnabled(boolean enabled);

    public boolean isFilterPartyChatEnabled();
    public IConfigManager setFilterPartyChatEnabled(boolean enabled);

    public boolean isInstantBedEnabled();
    public IConfigManager setInstantBedEnabled(boolean enabled);

    public boolean isPartyAutoRemoveOfflineEnabled();
    public IConfigManager setPartyAutoRemoveOfflineEnabled(boolean enabled);

    public boolean isDebugModeEnabled();
    public IConfigManager setDebugModeEnabled(boolean enabled);


    public String[] getGuildChatToggleCommands();
    public String[] getImprovedLobbyCommands();
    public String[] getInstantBedCommands();
    public String[] getLobbyProtectionCommands();
    public String[] getPartyAutoRemoveCommands();
    public String[] getPartyChatToggleCommands();
    public String[] getTipAndThankCommands();

    public String[] getLobbyTypes();
}
