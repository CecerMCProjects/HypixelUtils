package com.cecer1.modframework.forge.events.adapters;

import com.cecer1.modframework.common.events.EventAdapter;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.common.utils.MethodCallTimer;
import com.cecer1.modframework.forge.events.ForgeOnBungeeServerChangeEventData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class ForgeOnBungeeServerChangeAdapter extends EventAdapter {

    private static final long MAXIMUM_BUNGEE_WORLDLOADTYPES_DELAY = 500;

    private static enum BungeeMoveWorldLoadStage {
        NONE, OVERWORLD_FIRST, NETHER_SECOND, OVERWORLD_THIRD
    }

    private MethodCallTimer _timer;
    private BungeeMoveWorldLoadStage _stage;

    public ForgeOnBungeeServerChangeAdapter(EventManager manager) {
        super(manager);
        _timer = new MethodCallTimer();
    }

    private void advanceStage(boolean isNether)
    {
        // When a bungee server moves you between servers it sends 3 respawns: OVERWORLD, NETHER, OVERWORLD.
        // This method tracks that. If _stage is equal to OVERWORLD_THIRD after this method runs then a bungee server move is assumed to have just send the last respawn.

        boolean tooLong = _timer.call() > MAXIMUM_BUNGEE_WORLDLOADTYPES_DELAY;
        if(tooLong)
            _stage = BungeeMoveWorldLoadStage.NONE;

        if(isNether)
        {
            if(_stage == BungeeMoveWorldLoadStage.OVERWORLD_FIRST)
                _stage = BungeeMoveWorldLoadStage.NETHER_SECOND;
            else
                _stage = BungeeMoveWorldLoadStage.NONE;
        }
        else
        {
            if(_stage == BungeeMoveWorldLoadStage.NETHER_SECOND)
                _stage = BungeeMoveWorldLoadStage.OVERWORLD_THIRD;
            else
                _stage = BungeeMoveWorldLoadStage.OVERWORLD_FIRST;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        advanceStage(event.world.provider.isHellWorld);
        if(_stage != BungeeMoveWorldLoadStage.OVERWORLD_THIRD) // If this isn't the final stage of a bungee server move then we don't do anything.
            return;
        _stage = BungeeMoveWorldLoadStage.NONE; // Reset this after we are done checking.

        ForgeOnBungeeServerChangeEventData eventData = new ForgeOnBungeeServerChangeEventData();
        manager.fireEvent(eventData);
    }
}
