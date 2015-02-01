package com.cecer1.hypixelutils.data.config;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.chat.ChatOutputs;
import com.cecer1.hypixelutils.data.ConfigDataSourceValueBoolean;
import com.cecer1.hypixelutils.data.ConfigDataSourceValueInteger;
import com.cecer1.hypixelutils.data.ConfigDataSourceValueString;
import com.cecer1.hypixelutils.data.ConfigDataSourceValueStringList;
import com.cecer1.hypixelutils.events.eventdata.OnConfigDirtyEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;
import com.cecer1.hypixelutils.features.cloudconfig.IConfigMapServerReply;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.HttpResponse;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.async.Callback;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Map;
import java.util.concurrent.Future;

public class ConfigHelper {
    public Future<HttpResponse<String>> forceLoad() {
        ChatOutputs.printForcingConfigReload();
        return HypixelUtilsCore.cloudConfigServerGateway.getConfigMapFromServer(new IConfigMapServerReply() {
            @Override
            public void result(Map<String, String> result) {
                ConfigStore store = HypixelUtilsCore.internalConfig.configStore;
                store.clear();
                for(Map.Entry<String, String> entry : result.entrySet()) {
                    store.setRawValue(entry.getKey(), entry.getValue());
                }

                HypixelUtilsCore.eventManager.fireEvent(new OnConfigDirtyEventData(null)); // Announce that all values are dirty!
                HypixelUtilsCore.eventManager.fireEvent(new OnConfigUpdatedEventData(null));

                ChatOutputs.printForcedConfigReload();
            }
        });
    }
    
    public Future<HttpResponse<String>> forceSave() {
        ChatOutputs.printForcingConfigSave();
        return save(new Callback<String>() {
            @Override
            public void completed(HttpResponse<String> httpResponse) {
                ChatOutputs.printForcedConfigSave();
            }

            @Override
            public void failed(UnirestException e) {

            }

            @Override
            public void cancelled() {

            }
        });
    }
    public Future<HttpResponse<String>> save() {
        return save(null);
    }
    public Future<HttpResponse<String>> save(Callback<String> callback) {
       return HypixelUtilsCore.cloudConfigServerGateway.storeConfigMapToServer(HypixelUtilsCore.internalConfig.configStore.getAll(), callback);
    }

    public Future<HttpResponse<String>> delete() {
        ChatOutputs.printConfigDeleting();
        return HypixelUtilsCore.cloudConfigServerGateway.deleteConfig(new Callback<String>() {
            @Override
            public void completed(HttpResponse<String> httpResponse) {
                ChatOutputs.printConfigDeleted();
            }

            @Override
            public void failed(UnirestException e) {

            }

            @Override
            public void cancelled() {

            }
        });
    }
    
    public final ConfigDataSourceValueBoolean debugModeEnabled;

    public final ConfigDataSourceValueBoolean filterGuildChatEnabled;
    public final ConfigDataSourceValueBoolean filterPartyChatEnabled;
    public final ConfigDataSourceValueBoolean partyAutoRemoveOfflineEnabled;
    public final ConfigDataSourceValueBoolean bypassLobbyProtectionEnabled;
    public final ConfigDataSourceValueBoolean instantBedEnabled;

    public final ConfigDataSourceValueStringList guildChatToggleCommands;
    public final ConfigDataSourceValueStringList partyChatToggleCommands;
    public final ConfigDataSourceValueStringList improvedLobbyCommands;
    public final ConfigDataSourceValueStringList instantBedToggleCommands;
    public final ConfigDataSourceValueStringList partyAutoRemoveToggleCommands;
    public final ConfigDataSourceValueStringList tipAndThankCommands;
    public final ConfigDataSourceValueStringList ragequitCommands;
    public final ConfigDataSourceValueStringList bypassLobbyProtectionToggleCommands;

    public final ConfigDataSourceValueInteger configWindowPositionX;
    public final ConfigDataSourceValueInteger configWindowPositionY;


    public final ConfigDataSourceValueString welcomeMessage;

    public ConfigHelper() {
        debugModeEnabled = new ConfigDataSourceValueBoolean("debug.debugchat.enabled");

        filterGuildChatEnabled = new ConfigDataSourceValueBoolean("guild.filterchat.enabled");
        filterPartyChatEnabled = new ConfigDataSourceValueBoolean("party.filterchat.enabled");
        partyAutoRemoveOfflineEnabled = new ConfigDataSourceValueBoolean("party.removeoffline.enabled");
        bypassLobbyProtectionEnabled = new ConfigDataSourceValueBoolean("bypassprotection.lobbycommand.enabled");
        instantBedEnabled = new ConfigDataSourceValueBoolean("bypassprotection.lobbybed.enabled");

        guildChatToggleCommands = new ConfigDataSourceValueStringList("guild.filterchat.togglecommand.aliases", new String[] {"guildchattoggle", "gchattoggle", "gtoggle"} );
        partyChatToggleCommands = new ConfigDataSourceValueStringList("party.filterchat.togglecommand.aliases", new String[] {"partychattoggle", "pchattoggle", "ptoggle"});
        improvedLobbyCommands = new ConfigDataSourceValueStringList("improvedlobby.command.aliases", new String[] {"ilobby", "lobby", "hub", "main", "leave"});
        instantBedToggleCommands = new ConfigDataSourceValueStringList("bypassprotection.lobbybed.togglecommand.aliases", new String[] {"instantbed", "lobbybed"});
        partyAutoRemoveToggleCommands = new ConfigDataSourceValueStringList("party.removeoffline.aliases", new String[] {"partyautoremovetoggle", "pautoremovetoggle"});
        tipAndThankCommands = new ConfigDataSourceValueStringList("boosters.tipandthank.command.aliases", new String[]{"tipandthank"});
        ragequitCommands = new ConfigDataSourceValueStringList("ragequit.command.aliases", new String[] {"ragequit"});
        bypassLobbyProtectionToggleCommands = new ConfigDataSourceValueStringList("bypassprotection.lobbycommand.command.aliases", new String[] {"lobbyprotectiontoggle", "lobbylobbytoggle", "lobbyprotection", "lobbylobby"});

        configWindowPositionX = new ConfigDataSourceValueInteger("gui.configwindow.position.x");
        configWindowPositionY = new ConfigDataSourceValueInteger("gui.configwindow.position.y");

        welcomeMessage = new ConfigDataSourceValueString("general.welcomemessage.component");
    }
}
