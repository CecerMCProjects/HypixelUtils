package com.cecer1.hypixelutils.gui.frames;

import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.gui.GuiConfigManagerWrapper;
import com.cecer1.hypixelutils.gui.HypixelUtilsGuiFrame;
import org.darkstorm.minecraft.gui.component.SelectableComponent;
import org.darkstorm.minecraft.gui.component.basic.BasicCheckButton;
import org.darkstorm.minecraft.gui.listener.SelectableComponentListener;
import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;

import java.awt.*;

public class TogglesFrame extends HypixelUtilsGuiFrame {
    private GuiConfigManagerWrapper _configWrapper;
    
    private BasicCheckButton _isBypassLobbyProtectionEnabledButton;
    private BasicCheckButton _isFilterGuildChatEnabled;
    private BasicCheckButton _isFilterPartyChatEnabled;
    private BasicCheckButton _isInstantBedEnabled;
    private BasicCheckButton _isPartyAutoRemoveOfflineEnabled;

    public TogglesFrame(GuiConfigManagerWrapper configWrapper) {
        super("Toggles");
        _configWrapper = configWrapper;
    }

    @Override
    public void init() {
        if(_isBypassLobbyProtectionEnabledButton != null)
            return;
        
        this.setTheme(new SimpleTheme());
        
        _isBypassLobbyProtectionEnabledButton = new BasicCheckButton("Bypass /lobby Protection");
        _isFilterGuildChatEnabled = new BasicCheckButton("Filter Guild Chat");
        _isFilterPartyChatEnabled = new BasicCheckButton("Filter Party Chat");
        _isInstantBedEnabled = new BasicCheckButton("Instant Bed");
        _isPartyAutoRemoveOfflineEnabled = new BasicCheckButton("Auto Remove Offline Party Members");

        
        _isBypassLobbyProtectionEnabledButton.addSelectableComponentListener(new SelectableComponentListener() {
            @Override
            public void onSelectedStateChanged(SelectableComponent component) {
                _configWrapper.internalSetBypassLobbyProtectionEnabled(component.isSelected());
                ChatOutputs.printBypassLobbyProtectionEnabledStatus(component.isSelected());
            }
        });
        _isFilterGuildChatEnabled.addSelectableComponentListener(new SelectableComponentListener() {
            @Override
            public void onSelectedStateChanged(SelectableComponent component) {
                _configWrapper.internalSetFilterGuildChatEnabled(component.isSelected());
                ChatOutputs.printFilterGuildChatEnabledStatus(component.isSelected());
            }
        });
        _isFilterPartyChatEnabled.addSelectableComponentListener(new SelectableComponentListener() {
            @Override
            public void onSelectedStateChanged(SelectableComponent component) {
                _configWrapper.internalSetFilterPartyChatEnabled(component.isSelected());
                ChatOutputs.printFilterPartyChatEnabledStatus(component.isSelected());
            }
        });
        _isInstantBedEnabled.addSelectableComponentListener(new SelectableComponentListener() {
            @Override
            public void onSelectedStateChanged(SelectableComponent component) {
                _configWrapper.internalSetInstantBedEnabled(component.isSelected());
                ChatOutputs.printInstantBedEnabledStatus(component.isSelected());
            }
        });
        _isPartyAutoRemoveOfflineEnabled.addSelectableComponentListener(new SelectableComponentListener() {
            @Override
            public void onSelectedStateChanged(SelectableComponent component) {
                _configWrapper.internalSetPartyAutoRemoveOfflineEnabled(component.isSelected());
                ChatOutputs.printPartyAutoRemoveEnabledStatus(component.isSelected());
            }
        });
        
        this.add(_isBypassLobbyProtectionEnabledButton);
        this.add(_isFilterGuildChatEnabled);
        this.add(_isFilterPartyChatEnabled);
        this.add(_isInstantBedEnabled);
        this.add(_isPartyAutoRemoveOfflineEnabled);

        this.setX(200);
        this.setY(50);
        Dimension defaultDimension = getTheme().getUIForComponent(this).getDefaultSize(this);
        this.setWidth(defaultDimension.width);
        this.setHeight(defaultDimension.height);
        this.layoutChildren();
        this.setVisible(false);
        this.setMinimized(false);
        
        this.setPinned(false);
        this.setPinnable(false);
    }

    public TogglesFrame setBypassLobbyProtectionEnabled(boolean enabled) {
        _isBypassLobbyProtectionEnabledButton.setSelected(enabled);
        return this;
    }

    public TogglesFrame setFilterGuildChatEnabled(boolean enabled) {
        _isFilterGuildChatEnabled.setSelected(enabled);
        return this;
    }

    public TogglesFrame setFilterPartyChatEnabled(boolean enabled) {
        _isFilterPartyChatEnabled.setSelected(enabled);
        return this;
    }

    public TogglesFrame setInstantBedEnabled(boolean enabled) {
        _isInstantBedEnabled.setSelected(enabled);
        return this;
    }

    public TogglesFrame setPartyAutoRemoveOfflineEnabled(boolean enabled) {
        _isPartyAutoRemoveOfflineEnabled.setSelected(enabled);        
        return this;
    }
}
