package com.cecer1.modframework.liteloader.events.adapters;

import com.cecer1.modframework.common.events.EventAdapter;
import com.cecer1.modframework.common.events.EventManager;
import com.cecer1.modframework.common.utils.BungeeMoveHelper;
import com.cecer1.modframework.common.utils.WorldDimension;
import com.cecer1.modframework.liteloader.events.LiteLoaderOnBungeeServerChangeEventData;

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
        LiteLoaderOnBungeeServerChangeEventData eventData = new LiteLoaderOnBungeeServerChangeEventData();
        manager.fireEvent(eventData);
        return !eventData.isCanceled();
    }
}
