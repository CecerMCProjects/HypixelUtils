package com.cecer1.hypixelutils.events;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.modframework.common.events.IOnChatEventHandler;
import com.cecer1.modframework.common.utils.ChatUtilities;
import net.minecraft.client.Minecraft;

public class InstantBedProcessor implements IOnChatEventHandler
{
    @Override
    public void onChat(IOnChatEventData event) {
        if(!HypixelUtilsCore.config.isInstantBedEnabled())
            return;

        if (ChatUtilities.compareChatComponent(event.getMessage(), "{\"extra\":[{\"bold\":true,\"color\":\"green\",\"text\":\"Teleporting you to the lobby in 3 seconds... Right-click again to cancel the teleport!\"}],\"text\":\"\"}"))
        {
            HypixelUtilsCore.bypassLobbyCommandProtectionProcessor.enableOnce();
            Minecraft.getMinecraft().thePlayer.sendChatMessage("/lobby");
            event.setCanceled(true);
        }
    }
}
