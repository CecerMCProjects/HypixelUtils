package com.cecer1.hypixelutils.events;

import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.handlers.IEventHandler;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EventManager {
    private Set<SoftReference<IEventHandler>> _handlers;
    private Set<IEventHandler> _newHandlers;
    private Set<IEventHandler> _oldHandlers;
    
    public EventManager() {
        _handlers = new HashSet<SoftReference<IEventHandler>>();
        _oldHandlers = new HashSet<IEventHandler>();
        _newHandlers = new HashSet<IEventHandler>();
    }
    
    public EventManager registerEventHandlers(IEventHandler eventHandler) {
        _newHandlers.add(eventHandler);
        return this;
    }
    private void registerNewEventHandlers() {
        for(IEventHandler handler : _newHandlers) {
            _handlers.add(new SoftReference<IEventHandler>(handler));
        }
        _newHandlers.clear();
    }
    public EventManager deregisterEventHandlers(IEventHandler eventHandler) {
        _oldHandlers.add(eventHandler);
        return this;
    }
    private void deregisterOldEventHandlers() {
        Iterator<SoftReference<IEventHandler>> iterator = _handlers.iterator();
        while (iterator.hasNext()) {
            SoftReference<IEventHandler> handlerReference = iterator.next();
            IEventHandler handler = handlerReference.get();
            
            if(handler == null) {
                iterator.remove();
                continue;
            }
            
            if(_oldHandlers.contains(handler)) {
                iterator.remove();
                _oldHandlers.remove(handler);
                continue;
            }
        }
        _oldHandlers.clear();
    }

    public EventManager fireEvent(IEventData eventData) {
        try {
            deregisterOldEventHandlers();
            registerNewEventHandlers();
            for(SoftReference<IEventHandler> handlerReference : _handlers) {
                try {
                    IEventHandler handler = handlerReference.get();
                    if(handler != null) {
                        handler.onEvent(eventData);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return this;
    }

}
