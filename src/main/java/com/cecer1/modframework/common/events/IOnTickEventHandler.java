package com.cecer1.modframework.common.events;

import net.minecraft.util.IChatComponent;

public interface IOnTickEventHandler extends IEventHandler {
    public void onTick(IOnTickEventData event);

    static interface IOnTickEventData extends IEventData {
    }
}
