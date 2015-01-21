package com.cecer1.hypixelutils.gui;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.config.IConfigManager;
import com.cecer1.hypixelutils.gui.frames.TogglesFrame;

public class GuiConfigManagerWrapper implements IConfigManager {
    private IConfigManager _backingConfigManager;
    private TogglesFrame _togglesFrame;
    
    public GuiConfigManagerWrapper(IConfigManager backingConfigManager) {
        _backingConfigManager = backingConfigManager;

        _togglesFrame = new TogglesFrame(this);
        HypixelUtilsCore.userInterface.initAndAddFrame(_togglesFrame);

        loadBackingValues();
    }
    
    public void loadBackingValues() {
        setBypassLobbyProtectionEnabled(_backingConfigManager.isBypassLobbyProtectionEnabled());
        setFilterGuildChatEnabled(_backingConfigManager.isFilterGuildChatEnabled());
        setFilterPartyChatEnabled(_backingConfigManager.isFilterPartyChatEnabled());
        setInstantBedEnabled(_backingConfigManager.isInstantBedEnabled());
        setPartyAutoRemoveOfflineEnabled(_backingConfigManager.isPartyAutoRemoveOfflineEnabled());
    }
    
    public IConfigManager getBackingConfig() {
        return _backingConfigManager;
    }

    public TogglesFrame getTogglesFrame() {
        return _togglesFrame;
    }

    @Override
    public boolean isBypassLobbyProtectionEnabled() {
        return _backingConfigManager.isBypassLobbyProtectionEnabled();
    }

    @Override
    public IConfigManager setBypassLobbyProtectionEnabled(boolean enabled) {
        internalSetBypassLobbyProtectionEnabled(enabled);
        _togglesFrame.setBypassLobbyProtectionEnabled(enabled);
        return this;
    }

    @Override
    public boolean isFilterGuildChatEnabled() {
        return _backingConfigManager.isFilterGuildChatEnabled();
    }

    @Override
    public IConfigManager setFilterGuildChatEnabled(boolean enabled) {
        internalSetFilterGuildChatEnabled(enabled);
        _togglesFrame.setFilterGuildChatEnabled(enabled);
        return this;
    }

    @Override
    public boolean isFilterPartyChatEnabled() {
        return _backingConfigManager.isFilterPartyChatEnabled();
    }

    @Override
    public IConfigManager setFilterPartyChatEnabled(boolean enabled) {
        internalSetFilterPartyChatEnabled(enabled);
        _togglesFrame.setFilterPartyChatEnabled(enabled);
        return this;
    }

    @Override
    public boolean isInstantBedEnabled() {
        return _backingConfigManager.isInstantBedEnabled();
    }

    @Override
    public IConfigManager setInstantBedEnabled(boolean enabled) {
        internalSetInstantBedEnabled(enabled);
        _togglesFrame.setInstantBedEnabled(enabled);
        return this;
    }

    @Override
    public boolean isPartyAutoRemoveOfflineEnabled() {
        return _backingConfigManager.isPartyAutoRemoveOfflineEnabled();
    }

    @Override
    public IConfigManager setPartyAutoRemoveOfflineEnabled(boolean enabled) {
        internalSetPartyAutoRemoveOfflineEnabled(enabled);
        _togglesFrame.setPartyAutoRemoveOfflineEnabled(enabled);
        return this;
    }

    @Override
    public boolean isDebugModeEnabled() {
        return _backingConfigManager.isDebugModeEnabled();
    }

    @Override
    public IConfigManager setDebugModeEnabled(boolean enabled) {
        _backingConfigManager.setDebugModeEnabled(enabled);
        return this;
    }

    @Override
    public String[] getGuildChatToggleCommands() {
        return _backingConfigManager.getGuildChatToggleCommands();
    }

    @Override
    public String[] getImprovedLobbyCommands() {
        return _backingConfigManager.getImprovedLobbyCommands();
    }

    @Override
    public String[] getInstantBedCommands() {
        return _backingConfigManager.getInstantBedCommands();
    }

    @Override
    public String[] getLobbyProtectionCommands() {
        return _backingConfigManager.getLobbyProtectionCommands();
    }

    @Override
    public String[] getPartyAutoRemoveCommands() {
        return _backingConfigManager.getPartyAutoRemoveCommands();
    }

    @Override
    public String[] getPartyChatToggleCommands() {
        return _backingConfigManager.getPartyChatToggleCommands();
    }

    @Override
    public String[] getTipAndThankCommands() {
        return _backingConfigManager.getTipAndThankCommands();
    }

    @Override
    public String[] getRageQuitCommands() {
        return new String[0];
    }

    @Override
    public String[] getLobbyTypes() {
        return new String[0];
    }

    public void internalSetBypassLobbyProtectionEnabled(boolean enabled) {
        _backingConfigManager.setBypassLobbyProtectionEnabled(enabled);
    }
    public void internalSetFilterGuildChatEnabled(boolean enabled) {
        _backingConfigManager.setFilterGuildChatEnabled(enabled);
    }
    public void internalSetFilterPartyChatEnabled(boolean enabled) {
        _backingConfigManager.setFilterPartyChatEnabled(enabled);
    }
    public void internalSetInstantBedEnabled(boolean enabled) {
        _backingConfigManager.setInstantBedEnabled(enabled);
    }
    public void internalSetPartyAutoRemoveOfflineEnabled(boolean enabled) {
        _backingConfigManager.setPartyAutoRemoveOfflineEnabled(enabled);
    }
}
