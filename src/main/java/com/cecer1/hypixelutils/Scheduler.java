package com.cecer1.hypixelutils;

import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnTickEventData;
import com.cecer1.hypixelutils.events.handlers.IOnTickEventHandler;

import java.util.HashSet;
import java.util.Set;

public class Scheduler implements IOnTickEventHandler
{
    private Set<Task> _tasks;

    public Scheduler()
    {
        _tasks = new HashSet<Task>();
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
    
    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnTickEventData)
            onEvent((OnTickEventData)data);
    }

    @Override
    public void onEvent(OnTickEventData data) {
        Set<Task> expiredTasks = new HashSet<Task>();

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

    private class Task
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