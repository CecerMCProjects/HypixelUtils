package com.cecer1.hypixelutils.utils;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import static com.cecer1.hypixelutils.utils.ChatUtilities.QuickFormatting.*;

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

        IChatComponent debugChatComponent = new ChatComponentText("");

        if(HypixelUtilsCore.configHelper.debugModeEnabled.getValue(false)) {
            debugChatComponent
                .appendSibling(yellow(new ChatComponentText("Respawn Packet")))
                .appendSibling(white(new ChatComponentText(";")))
                .appendSibling(yellow(new ChatComponentText("Old Stage ")))
                .appendSibling(green(new ChatComponentText(Integer.toString(_stage))))
                .appendSibling(darkGreen(new ChatComponentText("/")))
                .appendSibling(green(new ChatComponentText(Integer.toString(EXPECTED_BUNGEE_MOVE_ORDER.length - 1))));
        }

        if(_stage+1 == EXPECTED_BUNGEE_MOVE_ORDER.length) { // We have reached are destination.
            _stage = -1;

            if(HypixelUtilsCore.configHelper.debugModeEnabled.getValue(false)) {
                debugChatComponent
                        .appendSibling(white(new ChatComponentText(";")))
                        .appendSibling(yellow(new ChatComponentText("Destination Reached: ")));
                if(dimension == WorldDimension.OVERWORLD) {
                    debugChatComponent.appendSibling(lightPurple(new ChatComponentText("OVERWORLD")));
                } else if(dimension == WorldDimension.NETHER) {
                    debugChatComponent.appendSibling(lightPurple(new ChatComponentText("NETHER")));
                } else if(dimension == WorldDimension.END) {
                    debugChatComponent.appendSibling(lightPurple(new ChatComponentText("END")));
                } else {
                    debugChatComponent.appendSibling(red(new ChatComponentText("UNKNOWN")));
                }

                ChatUtilities.printDebugChatComponent(debugChatComponent);
            }

            return true;
        }

        if(_stage+1 > EXPECTED_BUNGEE_MOVE_ORDER.length) { // Something has gone wrong as this should never be this high! Reset _stage to -1 and move on.
            _stage = -1;
        }

        WorldDimension expectedDimension = EXPECTED_BUNGEE_MOVE_ORDER[_stage+1];
        if(dimension == expectedDimension)
            _stage++;


        if(HypixelUtilsCore.configHelper.debugModeEnabled.getValue(false)) {
            debugChatComponent
                    .appendSibling(white(new ChatComponentText(";")))
                    .appendSibling(yellow(new ChatComponentText("New Stage ")))
                    .appendSibling(green(new ChatComponentText(Integer.toString(_stage))))
                    .appendSibling(darkGreen(new ChatComponentText("/")))
                    .appendSibling(green(new ChatComponentText(Integer.toString(EXPECTED_BUNGEE_MOVE_ORDER.length - 1))))
                    .appendSibling(white(new ChatComponentText(";")))
                    .appendSibling(yellow(new ChatComponentText("Dimension ")));
            if(dimension == WorldDimension.OVERWORLD) {
                debugChatComponent.appendSibling(lightPurple(new ChatComponentText("OVERWORLD")));
            } else if(dimension == WorldDimension.NETHER) {
                debugChatComponent.appendSibling(lightPurple(new ChatComponentText("NETHER")));
            } else if(dimension == WorldDimension.END) {
                debugChatComponent.appendSibling(lightPurple(new ChatComponentText("END")));
            } else {
                debugChatComponent.appendSibling(red(new ChatComponentText("UNKNOWN")));
            }

            ChatUtilities.printDebugChatComponent(debugChatComponent);
        }

        return false;
    }
}
