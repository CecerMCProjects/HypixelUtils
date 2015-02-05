package com.cecer1.hypixelutils.gui.components.compound;

import com.cecer1.hypixelutils.HypixelUtilsCore;
import com.cecer1.hypixelutils.data.DataSourceValue;
import com.cecer1.hypixelutils.events.eventdata.IEventData;
import com.cecer1.hypixelutils.events.eventdata.OnConfigUpdatedEventData;
import com.cecer1.hypixelutils.events.handlers.IOnConfigUpdatedEventHandler;
import com.cecer1.hypixelutils.gui.components.core.ComponentParent;
import com.cecer1.hypixelutils.gui.components.core.RectangleComponent;
import com.cecer1.hypixelutils.gui.components.core.SizableComponent;

public class SimpleCheckbox extends SizableComponent implements IOnConfigUpdatedEventHandler {
    private DataSourceValue<Boolean> _dataSource;
    public boolean isChecked() {
        Boolean value = _dataSource.getValue();
        return value != null && value.booleanValue();
    }
    public void setChecked(boolean checked) {
        _dataSource.setValue(Boolean.valueOf(checked));
    }
    public void toggleChecked() {
        setChecked(!isChecked());
    }
    
    private RectangleComponent _outerRect;
    private RectangleComponent _innerRect;
    private RectangleComponent _fillRect;

    public SimpleCheckbox(ComponentParent parent, DataSourceValue<Boolean> dataSource) {
        super(parent);

        _dataSource = dataSource;
        
        this.setClickThrough(false);
        
        final SimpleCheckbox checkbox;

        _outerRect = new RectangleComponent(this);
        _outerRect.setClickThrough(true);
        _outerRect.setColour(0x00, 0x00, 0x00, 0xFF);
        _outerRect.setVisible(true);

        _innerRect = new RectangleComponent(this);
        _innerRect.setClickThrough(true);
        _innerRect.setOffset(1, 1);
        _innerRect.setColour(0xFF, 0xFF, 0xFF, 0xFF);
        _innerRect.setVisible(true);

        _fillRect = new RectangleComponent(this);
        _fillRect.setClickThrough(true);
        _fillRect.setOffset(2, 2);
        _fillRect.setColour(0x00, 0x00, 0x00, 0xFF);

        HypixelUtilsCore.eventManager.registerEventHandlers(this);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        
        _outerRect.setSize(width, height);
        _innerRect.setSize(width-2, height-2);
        _fillRect.setSize(width-4, height-4);
    }
    
    public void onMouseUp(int x, int y) {
        toggleChecked();
    }


    @Override
    public void onEvent(IEventData data) {
        if(data instanceof OnConfigUpdatedEventData)
            onEvent((OnConfigUpdatedEventData)data);
    }
    @Override
    public void onEvent(OnConfigUpdatedEventData data) {
        _fillRect.setVisible(isChecked());
    }
}
