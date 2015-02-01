package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.HttpResponse;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.Unirest;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.async.Callback;
import com.cecer1.hypixelutils.jarjar.com.mashape.unirest.http.exceptions.UnirestException;
import com.cecer1.hypixelutils.utils.ChatUtilities;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.ChatComponentText;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CloudConfigServerGateway {
    public UUID configKey;
    
    public CloudConfigServerGateway(UUID configKey) {
        this.configKey = configKey;
    }
    
    private String buildJsonObject(Map<String, String> data) {
        JsonObject result = new JsonObject();
        JsonObject dataObject = new JsonObject();
        
        for(Map.Entry<String, String> dataEntry : data.entrySet()) {
            dataObject.addProperty(dataEntry.getKey(), dataEntry.getValue());
        }
        result.add("data", dataObject);
        return result.toString();
    }
    private Map<String, String> parseJsonGetReplyObject(String jsonConfigString) throws CloudConfigServerException {
        try {
            JsonObject jsonConfig = new JsonParser().parse(jsonConfigString).getAsJsonObject();
            if (!jsonConfig.get("success").getAsBoolean())
                throw new CloudConfigServerException(jsonConfig.get("cause").getAsString());

            Map<String, String> returnData = new HashMap<String, String>();
            JsonObject dataObject = jsonConfig.get("data").getAsJsonObject();

            for (Map.Entry<String, JsonElement> dataEntry : dataObject.entrySet()) {
                returnData.put(dataEntry.getKey(), dataEntry.getValue().getAsString());
            }
            return returnData;
        } catch (JsonSyntaxException e) {
            throw new CloudConfigServerException("The cloud config server responded with invalid JSON. Report this to Cecer!");
        }
    }
    
    public Future<HttpResponse<String>> getConfigMapFromServer(final IConfigMapServerReply callback) {
        return getConfigJsonStringFromServer(HypixelUtilsCore.CONFIG_SERVER, configKey.toString(), new Callback<String>() {
            @Override
            public void completed(HttpResponse<String> httpResponse) {
                try {
                    if(callback != null)
                        callback.result(parseJsonGetReplyObject(httpResponse.getBody()));
                } catch (CloudConfigServerException e) {
                    e.printStackTrace();
                    if(callback != null)
                        callback.result(null);
                }
            }

            @Override
            public void failed(UnirestException e) {
                CloudConfigServerException exception = new CloudConfigServerException("UnirestException thrown while getting the request from the server");
                exception.initCause(e);
                exception.printToChat("Failed to load config from server!");
                exception.printStackTrace();
                if(callback != null)
                    callback.result(null);
            }

            @Override
            public void cancelled() {
                CloudConfigServerException exception = new CloudConfigServerException("Unirest Request was cancelled!");
                exception.printToChat("Failed to load config from server!");
                exception.printStackTrace();
                if(callback != null)
                    callback.result(null);
            }
        });
    }

    public Map<String,String> getConfigMapFromServerSync() throws CloudConfigServerException {
        String jsonString = null;
        try {
            jsonString = getConfigJsonStringFromServer(HypixelUtilsCore.CONFIG_SERVER, configKey.toString(), null).get().getBody();
        } catch (InterruptedException e) {
            CloudConfigServerException exception = new CloudConfigServerException("InterruptedException while loading config from server.");
            exception.initCause(e);
            throw exception;
        } catch (ExecutionException e) {
            CloudConfigServerException exception = new CloudConfigServerException("ExecutionException while loading config from server.");
            exception.initCause(e);
            throw exception;
        }
        return parseJsonGetReplyObject(jsonString);
    }

    private static Future<HttpResponse<String>> getConfigJsonStringFromServer(String configServerPrefix, String key, Callback<String> callback) {
        String url = configServerPrefix + "/get";

        return Unirest.post(url)
                .field("key", key.toString())
                .field("version", HypixelUtilsCore.VERSION)
                .asStringAsync(callback);
    }

    public Future<HttpResponse<String>> storeConfigMapToServer(Map<String, String> data, Callback<String> callback) {
        return storeJsonConfig(HypixelUtilsCore.CONFIG_SERVER, configKey.toString(), buildJsonObject(data), callback);
    }
    private static Future<HttpResponse<String>> storeJsonConfig(String configServerPrefix, String key, String configJsonString, final Callback<String> callback) {
        String url = configServerPrefix + "/set";

        return Unirest.post(url)
                .field("key", key.toString())
                .field("version", HypixelUtilsCore.VERSION)
                .field("data", configJsonString)
                .asStringAsync(new Callback<String>() {
                    @Override
                    public void completed(HttpResponse<String> httpResponse) {
                        try {
                            JsonObject jsonConfig = new JsonParser().parse(httpResponse.getBody()).getAsJsonObject();
                            if (!jsonConfig.get("success").getAsBoolean()) {
                                UnirestException e = new UnirestException("Failure result from server.");
                                e.initCause(new CloudConfigServerException(jsonConfig.get("cause").getAsString()));
                                failed(e);
                                return;
                            }
                            if(callback != null)
                                callback.completed(httpResponse);
                        } catch (JsonSyntaxException e) {
                            UnirestException exception = new UnirestException("Invalid JSON result from server.");
                            exception.initCause(new CloudConfigServerException("The cloud config server responded with invalid JSON. Report this to Cecer!"));
                            failed(exception);
                        }
                    }

                    @Override
                    public void failed(UnirestException e) {
                        CloudConfigServerException exception = new CloudConfigServerException("UnirestException thrown while getting the request from the server");
                        exception.initCause(e);
                        exception.printToChat("Failed to save config to server!");
                        exception.printStackTrace();

                        if(callback != null)
                            callback.failed(e);
                    }

                    @Override
                    public void cancelled() {
                        CloudConfigServerException exception = new CloudConfigServerException("Unirest Request was cancelled!");
                        exception.printToChat("Failed to save config to server!");
                        exception.printStackTrace();
                        if(callback != null)
                            callback.cancelled();
                    }
                });
    }
    
    public Future<HttpResponse<String>> deleteConfig(final Callback<String> callback) {
        String url = HypixelUtilsCore.CONFIG_SERVER + "/del";

        return Unirest.post(url)
                .field("key", configKey.toString())
                .asStringAsync(new Callback<String>() {
                    @Override
                    public void completed(HttpResponse<String> httpResponse) {
                        try {
                            JsonObject jsonConfig = new JsonParser().parse(httpResponse.getBody()).getAsJsonObject();
                            if (!jsonConfig.get("success").getAsBoolean()) {
                                UnirestException e = new UnirestException("Failure result from server.");
                                e.initCause(new CloudConfigServerException(jsonConfig.get("cause").getAsString()));
                                failed(e);
                                return;
                            }
                            if(callback != null)
                                callback.completed(httpResponse);
                        } catch (JsonSyntaxException e) {
                            UnirestException exception = new UnirestException("Invalid JSON result from server.");
                            exception.initCause(new CloudConfigServerException("The cloud config server responded with invalid JSON. Report this to Cecer!"));
                            failed(exception);
                        }
                    }

                    @Override
                    public void failed(UnirestException e) {
                        CloudConfigServerException exception = new CloudConfigServerException("UnirestException thrown while getting the request from the server");
                        exception.initCause(e);
                        exception.printToChat("Failed to save config to server!");
                        exception.printStackTrace();

                        if(callback != null)
                            callback.failed(e);
                    }

                    @Override
                    public void cancelled() {
                        CloudConfigServerException exception = new CloudConfigServerException("Unirest Request was cancelled!");
                        exception.printToChat("Failed to save config to server!");
                        exception.printStackTrace();
                        if(callback != null)
                            callback.cancelled();
                    }
                });
    }

    public static class CloudConfigServerException extends Exception {
        public CloudConfigServerException(String message) {
            super(message);
        }

        public void printToChat(String friendlyErrorPrefix) {
            this.printStackTrace();

            ChatUtilities.printChatComponent(new ChatComponentText("")
                    .appendSibling(ChatUtilities.QuickFormatting.darkRed(new ChatComponentText("ERROR: ")))
                    .appendSibling(ChatUtilities.QuickFormatting.red(new ChatComponentText(friendlyErrorPrefix))));
            
            Throwable throwable = this;
            int i = 0;
            while(throwable.getCause() != null) {
                ChatUtilities.printChatComponent(new ChatComponentText("")
                        .appendSibling(ChatUtilities.QuickFormatting.red(new ChatComponentText("Message (" + i + "): ")))
                        .appendSibling(ChatUtilities.QuickFormatting.white(new ChatComponentText(throwable.getMessage()))));
                throwable = throwable.getCause();
                i++;
            }
            ChatUtilities.printChatComponent(new ChatComponentText("")
                    .appendSibling(ChatUtilities.QuickFormatting.red(new ChatComponentText("Message (" + i + "): ")))
                    .appendSibling(ChatUtilities.QuickFormatting.white(new ChatComponentText(throwable.getMessage()))));
        }
    }
}
