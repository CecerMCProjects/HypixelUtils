package com.cecer1.modframework.common.events;

public interface IOnRenderEventHandler extends IEventHandler {
    public void onRender(IOnRenderEventData event);

    static interface IOnRenderEventData extends IEventData {
        public boolean isIngame();
    }
}
