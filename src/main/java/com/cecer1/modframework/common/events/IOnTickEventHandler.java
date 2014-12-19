package com.cecer1.modframework.common.events;

public interface IOnTickEventHandler extends IEventHandler {
    public void onTick(IOnTickEventData event);

    static interface IOnTickEventData extends IEventData {
    }
}
