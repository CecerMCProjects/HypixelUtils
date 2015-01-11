package com.cecer1.hypixelutils.features.general;

import com.cecer1.hypixelutils.features.general.HypixelStateUpdateType;
import com.cecer1.modframework.common.events.IEventData;
import com.cecer1.modframework.common.events.IEventHandler;

public interface IOnHypixelStateUpdatedEventHandler extends IEventHandler {
    public void onHypixelStateUpdated(IOnHypixelStateUpdatedEventData eventData);

    static interface IOnHypixelStateUpdatedEventData extends IEventData {

        public HypixelStateUpdateType getUpdateType();
    }
}
