package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.config.IConfigManager;
import com.cecer1.modframework.common.utils.MapUtil;
import com.cecer1.modframework.common.utils.MiscUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

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
    public static String getConfigJsonStringFromServer(String configServerPrefix, String key) throws CloudConfigServerException {
        Map<String, String> queryMap = new HashMap<String ,String>();
        queryMap.put("key", key.toString());
        queryMap.put("version", HypixelUtilsCore.VERSION);

        String url = configServerPrefix + "/get?" + MapUtil.queryMapToString(queryMap);
        String response = getResponseFromServer(url);

        JsonObject jsonConfig = new JsonParser().parse(response).getAsJsonObject();
        if(!jsonConfig.get("success").getAsBoolean())
            throw new CloudConfigServerException(jsonConfig.get("cause").getAsString());

        return response;
    }
    public static String storeJsonConfig(String configServerPrefix, String key, String configJsonString) throws CloudConfigServerException {
        Map<String, String> queryMap = new HashMap<String ,String>();
        queryMap.put("key", key.toString());
        queryMap.put("version", HypixelUtilsCore.VERSION);
        queryMap.put("config", configJsonString);

        String url = configServerPrefix + "/set?" + MapUtil.queryMapToString(queryMap);
        String response = getResponseFromServer(url);

        JsonObject jsonConfig = new JsonParser().parse(response).getAsJsonObject();
        if(!jsonConfig.get("success").getAsBoolean())
            throw new CloudConfigServerException(jsonConfig.get("cause").getAsString());

        return response;
    }
    public static String deleteConfig(String configServerPrefix, String key) throws CloudConfigServerException {
        Map<String, String> queryMap = new HashMap<String ,String>();
        queryMap.put("key", key.toString());

        String url = configServerPrefix + "/del?" + MapUtil.queryMapToString(queryMap);
        String response = getResponseFromServer(url);

        JsonObject jsonConfig = new JsonParser().parse(response).getAsJsonObject();
        if(!jsonConfig.get("success").getAsBoolean())
            throw new CloudConfigServerException(jsonConfig.get("cause").getAsString());

        return response;
    }

    private static String getResponseFromServer(String url) {

        try {
            HttpURLConnection con = (HttpURLConnection)new URL(url).openConnection();
            String result = MiscUtils.getWholeInputStreamAsString(con.getInputStream());
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "{\"success\":false,\"cause\":\"MalformedURLException thrown while getting the request from the server\"}";
        } catch (ConnectException e) {
            e.printStackTrace();
            return "{\"success\":false,\"cause\":\"ConnectException thrown while getting the request from the server\"}";
        } catch (IOException e) {
            e.printStackTrace();
            return "{\"success\":false,\"cause\":\"IOException thrown while getting the request from the server\"}";
        }
    }
}
