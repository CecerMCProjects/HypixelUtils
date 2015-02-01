package com.cecer1.hypixelutils.gui.windows;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.gui.components.compound.LabeledCheckbox;
import com.cecer1.hypixelutils.gui.components.compound.Window;
import com.cecer1.hypixelutils.gui.components.core.ComponentParent;

import java.util.ArrayList;

public class ConfigWindow extends Window {
    private LabeledCheckbox _isBypassLobbyProtectionEnabledCheckbox;
    private LabeledCheckbox _isFilterGuildChatEnabledCheckbox;
    private LabeledCheckbox _isFilterPartyChatEnabledCheckbox;
    private LabeledCheckbox _isInstantBedEnabledCheckbox;
    private LabeledCheckbox _isPartyAutoRemoveOfflineEnabledCheckbox;
    
    public ConfigWindow(ComponentParent parent) {
        super(parent);
        this.setTitle("Config");
        this.setSize(148, 79);

        _isBypassLobbyProtectionEnabledCheckbox = new LabeledCheckbox(this, HypixelUtilsCore.configHelper.bypassLobbyProtectionEnabled);
        _isBypassLobbyProtectionEnabledCheckbox.setText("Bypass /lobby Protection");
        _isBypassLobbyProtectionEnabledCheckbox.setOffset(3, 14);
        _isBypassLobbyProtectionEnabledCheckbox.setSize(142, 10);
        _isBypassLobbyProtectionEnabledCheckbox.setVisible(true);
        _isBypassLobbyProtectionEnabledCheckbox.hoverText = new ArrayList<String>();
        _isBypassLobbyProtectionEnabledCheckbox.hoverText.add("Removes the need to type /lobby twice");
        _isBypassLobbyProtectionEnabledCheckbox.hoverText.add("when trying to leave a game.");

        _isFilterGuildChatEnabledCheckbox = new LabeledCheckbox(this, HypixelUtilsCore.configHelper.filterGuildChatEnabled);
        _isFilterGuildChatEnabledCheckbox.setText("Filter Guild Chat");
        _isFilterGuildChatEnabledCheckbox.setOffset(3, 27);
        _isFilterGuildChatEnabledCheckbox.setSize(142, 10);
        _isFilterGuildChatEnabledCheckbox.setVisible(true);
        _isFilterGuildChatEnabledCheckbox.hoverText = new ArrayList<String>();
        _isFilterGuildChatEnabledCheckbox.hoverText.add("Prevents guild chat from showing.");

        _isFilterPartyChatEnabledCheckbox = new LabeledCheckbox(this, HypixelUtilsCore.configHelper.filterPartyChatEnabled);
        _isFilterPartyChatEnabledCheckbox.setText("Filter Party Chat");
        _isFilterPartyChatEnabledCheckbox.setOffset(3, 40);
        _isFilterPartyChatEnabledCheckbox.setSize(142, 10);
        _isFilterPartyChatEnabledCheckbox.setVisible(true);
        _isFilterPartyChatEnabledCheckbox.hoverText = new ArrayList<String>();
        _isFilterPartyChatEnabledCheckbox.hoverText.add("Prevents party chat from showing.");

        _isInstantBedEnabledCheckbox = new LabeledCheckbox(this, HypixelUtilsCore.configHelper.instantBedEnabled);
        _isInstantBedEnabledCheckbox.setText("Instant Bed");
        _isInstantBedEnabledCheckbox.setOffset(3, 53);
        _isInstantBedEnabledCheckbox.setSize(142, 10);
        _isInstantBedEnabledCheckbox.setVisible(true);
        _isInstantBedEnabledCheckbox.hoverText = new ArrayList<String>();
        _isInstantBedEnabledCheckbox.hoverText.add("Removes the delay when using the bed to leave a game.");

        _isPartyAutoRemoveOfflineEnabledCheckbox = new LabeledCheckbox(this, HypixelUtilsCore.configHelper.partyAutoRemoveOfflineEnabled);
        _isPartyAutoRemoveOfflineEnabledCheckbox.setText("Party Auto Remove");
        _isPartyAutoRemoveOfflineEnabledCheckbox.setOffset(3, 66);
        _isPartyAutoRemoveOfflineEnabledCheckbox.setSize(142, 10);
        _isPartyAutoRemoveOfflineEnabledCheckbox.setVisible(true);
        _isPartyAutoRemoveOfflineEnabledCheckbox.hoverText = new ArrayList<String>();
        _isPartyAutoRemoveOfflineEnabledCheckbox.hoverText.add("Automatically removes offline party members");
        _isPartyAutoRemoveOfflineEnabledCheckbox.hoverText.add("if they prevent you joining a game.");
    }

    @Override
    public void onOffsetChanged() {
        HypixelUtilsCore.configHelper.configWindowPositionX.setValueNoSave(_offsetX);
        HypixelUtilsCore.configHelper.configWindowPositionY.setValueNoSave(_offsetY);
    }
}
