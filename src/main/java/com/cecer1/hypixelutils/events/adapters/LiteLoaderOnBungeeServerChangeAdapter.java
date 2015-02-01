package com.cecer1.hypixelutils.events.adapters;

import com.cecer1.hypixelutils.events.EventAdapter;
import com.cecer1.hypixelutils.events.EventManager;
import com.cecer1.hypixelutils.events.eventdata.OnBungeeServerChangeEventData;
import com.cecer1.hypixelutils.utils.BungeeMoveHelper;
import com.cecer1.hypixelutils.utils.WorldDimension;

public class LiteLoaderOnBungeeServerChangeAdapter extends EventAdapter {
    private BungeeMoveHelper _helper;

    public LiteLoaderOnBungeeServerChangeAdapter(EventManager manager) {
        super(manager);
        _helper = new BungeeMoveHelper();
    }

    public boolean onRespawn(WorldDimension dimension) {
        if(_helper.testRespawn(dimension))
            return trigger();
        return true;
    }

    public boolean trigger() {
        OnBungeeServerChangeEventData eventData = new OnBungeeServerChangeEventData();
        manager.fireEvent(eventData);
        return !eventData.isCanceled();
    }
}
