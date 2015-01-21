package com.cecer1.hypixelutils.features.cloudconfig;

import com.cecer1.hypixelutils.UtilityMethods;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class CloudConfigServerException extends Exception {
    public CloudConfigServerException(String message) {
        super(message);
    }

    public void printToChat(String friendlyErrorPrefix) {
        this.printStackTrace();

        if(Minecraft.getMinecraft().getCurrentServerData() == null)
            return; // Minecraft isn't done loading yet. Nothing left to do.

        ChatUtilities.printChatComponent(UtilityMethods.getHypixelUtilsChatComponentPrefix()
                .appendSibling(new ChatComponentText("ERROR: ").setChatStyle(ChatUtilities.ChatPresets.DARK_RED))
                .appendSibling(new ChatComponentText(friendlyErrorPrefix).setChatStyle(ChatUtilities.ChatPresets.RED)));

        ChatUtilities.printChatComponent(UtilityMethods.getHypixelUtilsChatComponentPrefix()
                .appendSibling(new ChatComponentText("Cause: ").setChatStyle(ChatUtilities.ChatPresets.RED))
                .appendSibling(new ChatComponentText(this.getMessage()).setChatStyle(ChatUtilities.ChatPresets.WHITE)));
    }
}
