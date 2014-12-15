package com.cecer1.hypixelutils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class Scheduler
{
    private List<Task> _tasks;

    public Scheduler()
    {
        _tasks = new ArrayList<Task>();
    }
    public Scheduler scheduleTask(Runnable runnable, long delayInTicks)
    {
        _tasks.add(new Task(runnable, delayInTicks, false));
        return this;
    }
    public Scheduler scheduleRepeatingTask(Runnable runnable, long delayInTicks)
    {
        _tasks.add(new Task(runnable, delayInTicks, true));
        return this;
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event)
    {
        if(event.phase == TickEvent.Phase.START)
            return;

        List<Task> expiredTasks = new ArrayList<Task>();
        for(Task task : _tasks)
        {
            task.tick();
            if(!task.isActive())
                expiredTasks.add(task);
        }

        for(Task task : expiredTasks)
        {
            _tasks.remove(task);
        }
    }

    private static class Task
    {
        private Runnable _runnable;
        private long _remainingTicks;
        private long _loopLength;

        private Task(Runnable runnable, long delayInTicks, boolean loop)
        {
            _runnable = runnable;
            _remainingTicks = delayInTicks;
            _loopLength = (loop ? delayInTicks : -1);
        }

        private void tick()
        {
            _remainingTicks--;
            if(_remainingTicks == 0)
            {
                _runnable.run();

                if (_loopLength > 0)
                    _remainingTicks = _loopLength;
            }
        }

        private boolean isActive()
        {
            return _remainingTicks > 0;
        }

        private void kill()
        {
            _loopLength = -1;
            _remainingTicks = -1;
        }
    }
}
