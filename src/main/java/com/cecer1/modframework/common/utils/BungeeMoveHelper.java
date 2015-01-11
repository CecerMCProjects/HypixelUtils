package com.cecer1.modframework.common.utils;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.UtilityMethods;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/**
 * Helper class to track when moving instances while connected to the bungee server.
 */
public class BungeeMoveHelper {
    private static final long MAXIMUM_BUNGEE_WORLDLOADTYPES_DELAY = 500;
    private static final WorldDimension[] EXPECTED_BUNGEE_MOVE_ORDER = new WorldDimension[] {
        WorldDimension.END,
        WorldDimension.NETHER
    };
    private MethodCallTimer _timer;
    private int _stage; // -1 = None

    public BungeeMoveHelper() {
        _timer = new MethodCallTimer();
    }

    /**
     * To be called whenever a respawn packet arrives from the server.
     * @return Returns true if this was detected as a bungee move.
     */
    public boolean testRespawn(WorldDimension dimension) {
        // When the bungee server moves you between servers it appears to send 3 respawns: END, NETHER, OVERWORLD. This method tracks that.
        // TODO: What happens when you move to a nether/end world? Prediction: END, NETHER, <TARGET_WORLD_DIMENSION>?
        boolean tooLong = _timer.call() > MAXIMUM_BUNGEE_WORLDLOADTYPES_DELAY;
        if(tooLong)
            _stage = -1;

        IChatComponent debugChatComponent = UtilityMethods.getHypixelUtilsChatComponentDebugPrefix();

        if(HypixelUtilsCore.config.isDebugModeEnabled()) {
            debugChatComponent
                .appendSibling(new ChatComponentText("Respawn Packet").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
                .appendSibling(new ChatComponentText(";").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)))
                .appendSibling(new ChatComponentText("Old Stage ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
                .appendSibling(new ChatComponentText(Integer.toString(_stage)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)))
                .appendSibling(new ChatComponentText("/").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GREEN)))
                .appendSibling(new ChatComponentText(Integer.toString(EXPECTED_BUNGEE_MOVE_ORDER.length - 1)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
        }

        if(_stage+1 == EXPECTED_BUNGEE_MOVE_ORDER.length) { // We have reached are destination.
            _stage = -1;

            if(HypixelUtilsCore.config.isDebugModeEnabled()) {
                debugChatComponent
                        .appendSibling(new ChatComponentText(";").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)))
                        .appendSibling(new ChatComponentText("Destination Reached: ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
                if(dimension == WorldDimension.OVERWORLD) {
                    debugChatComponent.appendSibling(new ChatComponentText("OVERWORLD").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
                } else if(dimension == WorldDimension.NETHER) {
                    debugChatComponent.appendSibling(new ChatComponentText("NETHER").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
                } else if(dimension == WorldDimension.END) {
                    debugChatComponent.appendSibling(new ChatComponentText("END").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
                } else {
                    debugChatComponent.appendSibling(new ChatComponentText("UNKNOWN").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                }

                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(debugChatComponent);
            }

            return true;
        }

        if(_stage+1 > EXPECTED_BUNGEE_MOVE_ORDER.length) { // Something has gone wrong as this should never be this high! Reset _stage to -1 and move on.
            _stage = -1;
        }

        WorldDimension expectedDimension = EXPECTED_BUNGEE_MOVE_ORDER[_stage+1];
        if(dimension == expectedDimension)
            _stage++;


        if(HypixelUtilsCore.config.isDebugModeEnabled()) {
            debugChatComponent
                .appendSibling(new ChatComponentText(";").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)))
                .appendSibling(new ChatComponentText("New Stage ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)))
                .appendSibling(new ChatComponentText(Integer.toString(_stage)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)))
                .appendSibling(new ChatComponentText("/").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GREEN)))
                .appendSibling(new ChatComponentText(Integer.toString(EXPECTED_BUNGEE_MOVE_ORDER.length - 1)).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)))
                .appendSibling(new ChatComponentText(";").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.WHITE)))
                .appendSibling(new ChatComponentText("Dimension ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
            if(dimension == WorldDimension.OVERWORLD) {
                debugChatComponent.appendSibling(new ChatComponentText("OVERWORLD").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
            } else if(dimension == WorldDimension.NETHER) {
                debugChatComponent.appendSibling(new ChatComponentText("NETHER").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
            } else if(dimension == WorldDimension.END) {
                debugChatComponent.appendSibling(new ChatComponentText("END").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
            } else {
                debugChatComponent.appendSibling(new ChatComponentText("UNKNOWN").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
            }

            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(debugChatComponent);
        }

        return false;
    }
}
