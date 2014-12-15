package com.cecer1.hypixelutils.chatprocessors;

import com.cecer1.hypixelutils.Utility;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.world.WorldEvent;

public abstract class BaseBungeeMoveServerProcessor extends  BaseProcessor
{
    public BaseBungeeMoveServerProcessor()
    {
        super();
        _timer = new Utility.MethodCallTimer();
        _stage = BungeeMoveWorldLoadStage.NONE;
    }

    public BaseBungeeMoveServerProcessor(Property configProperty, boolean enabledByDefault)
    {
        super(configProperty, enabledByDefault);
        _timer = new Utility.MethodCallTimer();
    }

    private static final long MAXIMUM_BUNGEE_WORLDLOADTYPES_DELAY = 50000;

    public static enum BungeeMoveWorldLoadStage {
        NONE, OVERWORLD_FIRST, NETHER_SECOND, OVERWORLD_THIRD
    }
    private Utility.MethodCallTimer _timer;
    private BungeeMoveWorldLoadStage _stage;

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
    public void internalOnWorldLoad(WorldEvent.Load event)
    {
        advanceStage(event.world.provider.isHellWorld);
        if(_stage != BungeeMoveWorldLoadStage.OVERWORLD_THIRD) // If this isn't the final stage of a bungee server move then we don't do anything.
            return;
        _stage = BungeeMoveWorldLoadStage.NONE; // Reset this after we are done checking.

		if(isEnabledAtAll())
            onBungeeMoveServer(event);
	}
	
	public abstract void onBungeeMoveServer(WorldEvent.Load event);
}
