package com.cecer1.modframework.common.events;

import java.util.HashSet;
import java.util.Set;

public class EventManager {
    private Set<IOnChatEventHandler> _onChatEventHandlers;
    private Set<IOnBungeeServerChangeEventHandler> _onBungeeServerChangeEventHandlers;
    private Set<IOnTickEventHandler> _onTickEventHandlers;
    private Set<IOnRenderEventHandler> _onRenderEventHandlers;
    private Set<IOnConnectEventHandler> _onConnectEventHandlers;

    public EventManager() {
        _onChatEventHandlers = new HashSet<IOnChatEventHandler>();
        _onBungeeServerChangeEventHandlers = new HashSet<IOnBungeeServerChangeEventHandler>();
        _onTickEventHandlers = new HashSet<IOnTickEventHandler>();
        _onRenderEventHandlers = new HashSet<IOnRenderEventHandler>();
        _onConnectEventHandlers = new HashSet<IOnConnectEventHandler>();
    }

    public EventManager registerEventHandlers(IEventHandler eventHandler) {
        if(eventHandler instanceof IOnChatEventHandler)
            _onChatEventHandlers.add((IOnChatEventHandler) eventHandler);

        if(eventHandler instanceof IOnBungeeServerChangeEventHandler)
            _onBungeeServerChangeEventHandlers.add((IOnBungeeServerChangeEventHandler) eventHandler);

        if(eventHandler instanceof IOnTickEventHandler)
            _onTickEventHandlers.add((IOnTickEventHandler) eventHandler);

        if(eventHandler instanceof IOnRenderEventHandler)
            _onRenderEventHandlers.add((IOnRenderEventHandler) eventHandler);

        if(eventHandler instanceof IOnConnectEventHandler)
            _onConnectEventHandlers.add((IOnConnectEventHandler) eventHandler);

        return this;
    }
    public EventManager deregisterEventHandlers(IEventHandler eventHandler) {
        if(_onChatEventHandlers.contains(eventHandler))
            _onChatEventHandlers.remove(eventHandler);

        if(_onBungeeServerChangeEventHandlers.contains(eventHandler))
            _onBungeeServerChangeEventHandlers.remove(eventHandler);

        if(_onTickEventHandlers.contains(eventHandler))
            _onTickEventHandlers.remove(eventHandler);

        if(_onRenderEventHandlers.contains(eventHandler))
            _onRenderEventHandlers.remove(eventHandler);

        if(_onConnectEventHandlers.contains(eventHandler))
            _onConnectEventHandlers.remove(eventHandler);

        return this;
    }

    public EventManager fireEvent(IEventData eventData) {
        if(eventData instanceof IOnChatEventHandler.IOnChatEventData) {
            IOnChatEventHandler.IOnChatEventData castedEventData = (IOnChatEventHandler.IOnChatEventData) eventData;
            for(IOnChatEventHandler handler : _onChatEventHandlers) {
                handler.onChat(castedEventData);
            }
        }

        if(eventData instanceof IOnBungeeServerChangeEventHandler.IOnBungeeServerChangeEventData) {
            IOnBungeeServerChangeEventHandler.IOnBungeeServerChangeEventData castedEventData = (IOnBungeeServerChangeEventHandler.IOnBungeeServerChangeEventData) eventData;
            for(IOnBungeeServerChangeEventHandler handler : _onBungeeServerChangeEventHandlers) {
                handler.onBungeeServerChange(castedEventData);
            }
        }

        if(eventData instanceof IOnTickEventHandler.IOnTickEventData) {
            IOnTickEventHandler.IOnTickEventData castedEventData = (IOnTickEventHandler.IOnTickEventData) eventData;
            for(IOnTickEventHandler handler : _onTickEventHandlers) {
                handler.onTick(castedEventData);
            }
        }

        if(eventData instanceof IOnRenderEventHandler.IOnRenderEventData) {
            IOnRenderEventHandler.IOnRenderEventData castedEventData = (IOnRenderEventHandler.IOnRenderEventData) eventData;
            for(IOnRenderEventHandler handler : _onRenderEventHandlers) {
                handler.onRender(castedEventData);
            }
        }

        if(eventData instanceof IOnConnectEventHandler.IOnConnectEventData) {
            IOnConnectEventHandler.IOnConnectEventData castedEventData = (IOnConnectEventHandler.IOnConnectEventData) eventData;
            for(IOnConnectEventHandler handler : _onConnectEventHandlers) {
                handler.onConnect(castedEventData);
            }
        }

        return this;
    }

}
