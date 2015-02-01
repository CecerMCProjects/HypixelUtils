package com.cecer1.hypixelutils.events.eventdata;

import com.cecer1.hypixelutils.features.general.HypixelStateUpdateType;

public class OnHypixelStateUpdatedEventData implements IEventData {

    private HypixelStateUpdateType _type;
    public OnHypixelStateUpdatedEventData(HypixelStateUpdateType updateType) {
        _type = updateType;
    }

    public HypixelStateUpdateType getUpdateType() {
        return _type;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public IEventData setCanceled(boolean cancel) {
        return this;
    }
}
