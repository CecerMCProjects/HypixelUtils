package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.config.IConfigManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.HttpResponse;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.Unirest;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.async.Callback;

import java.util.concurrent.Future;

public class CloudConfigServerGateway {
    public static String getConfigStringFromConfigManager(IConfigManager configManager) {
        JsonObject result = new JsonObject();
        JsonObject enabledFeatures = new JsonObject();
        enabledFeatures.addProperty("filterGuildChat", configManager.isFilterGuildChatEnabled());
        enabledFeatures.addProperty("filterPartyChat", configManager.isFilterPartyChatEnabled());
        enabledFeatures.addProperty("partyAutoRemove", configManager.isPartyAutoRemoveOfflineEnabled());
        enabledFeatures.addProperty("instantBed", configManager.isInstantBedEnabled());
        enabledFeatures.addProperty("bypassLobbyProtection", configManager.isBypassLobbyProtectionEnabled());
        enabledFeatures.addProperty("debugMode", configManager.isDebugModeEnabled());

        result.add("enabledFeatures", enabledFeatures);
        return result.toString();
    }
    public static void applyJsonConfig(IConfigManager configManager, String jsonConfigString) throws CloudConfigServerException {
        JsonObject jsonConfig = new JsonParser().parse(jsonConfigString).getAsJsonObject();
        if(!jsonConfig.get("success").getAsBoolean())
            throw new CloudConfigServerException(jsonConfig.get("cause").getAsString());

        JsonObject configData = jsonConfig.getAsJsonObject("configData");
        JsonObject enabledFeatures = configData.getAsJsonObject("enabledFeatures");
        configManager.setFilterGuildChatEnabled(enabledFeatures.get("filterGuildChat").getAsBoolean());
        configManager.setFilterPartyChatEnabled(enabledFeatures.get("filterPartyChat").getAsBoolean());
        configManager.setPartyAutoRemoveOfflineEnabled(enabledFeatures.get("partyAutoRemove").getAsBoolean());
        configManager.setInstantBedEnabled(enabledFeatures.get("instantBed").getAsBoolean());
        configManager.setBypassLobbyProtectionEnabled(enabledFeatures.get("bypassLobbyProtection").getAsBoolean());
        configManager.setDebugModeEnabled(enabledFeatures.get("debugMode").getAsBoolean());
    }
    public static Future<HttpResponse<String>> getConfigJsonStringFromServer(String configServerPrefix, String key, Callback<String> callback) {
        String url = configServerPrefix + "/get";

        return Unirest.post(url)
                .field("key", key.toString())
                .field("version", HypixelUtilsCore.VERSION)
                .asStringAsync(callback);

    }
    public static Future<HttpResponse<String>> storeJsonConfig(String configServerPrefix, String key, String configJsonString, Callback<String> callback) {
        String url = configServerPrefix + "/set";

        return Unirest.post(url)
                .field("key", key.toString())
                .field("version", HypixelUtilsCore.VERSION)
                .field("config", configJsonString)
                .asStringAsync(callback);
    }
    public static Future<HttpResponse<String>> deleteConfig(String configServerPrefix, String key, Callback<String> callback) {
        String url = configServerPrefix + "/del";

        return Unirest.post(url)
                .field("key", key.toString())
                .asStringAsync(callback);
    }
}
