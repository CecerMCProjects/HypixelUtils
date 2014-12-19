package com.cecer1.modframework.common.events;

public interface IOnBungeeServerChangeEventHandler extends IEventHandler {
    public void onBungeeServerChange(IOnBungeeServerChangeEventData eventData);

    static interface IOnBungeeServerChangeEventData extends IEventData {
    }
}
