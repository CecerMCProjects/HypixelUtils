package com.cecer1.hypixelutils.features.general;

import com.cecer1.modframework.common.events.IEventData;

public class OnHypixelStateUpdatedEventData implements IOnHypixelStateUpdatedEventHandler.IOnHypixelStateUpdatedEventData {

    private HypixelStateUpdateType _type;
    public OnHypixelStateUpdatedEventData(HypixelStateUpdateType updateType) {
        _type = updateType;
    }

    @Override
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
