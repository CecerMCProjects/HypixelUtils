package com.cecer1.hypixelutils.gui.components.compound;

import com.cecer1.hypixelutils.data.DataSourceValue;
import com.cecer1.hypixelutils.gui.components.core.*;

public class LabeledCheckbox extends SizableComponent {
    public boolean isChecked() {
        return _checkbox.isChecked();
    }

    public void setChecked(boolean checked) {
        _checkbox.setChecked(checked);
    }

    public void toggleChecked() {
        _checkbox.toggleChecked();
    }

    private SimpleCheckbox _checkbox;
    private TextLabelComponent _text;

    public LabeledCheckbox(ComponentParent parent, DataSourceValue<Boolean> dataSource) {
        super(parent);
        
        this.setClickThrough(false);
        
        _checkbox = new SimpleCheckbox(this, dataSource);
        _checkbox.setVisible(true);
        
        _text = new TextLabelComponent(this);
        _text.setVisible(true);
        _text.setHorizontalAlignment(HorizontalTextAlignment.LEFT);
        _text.setVerticalAlignment(VerticalTextAlignment.CENTRE);
        _text.setColour(0xFF, 0xFF, 0xFF, 0xFF);
    }
    
    @Override
    public void onMouseUp(int x, int y) {
        _checkbox.toggleChecked();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        
        _checkbox.setSize(height, height); // Yes, this is intentionally height for both.
        _text.setSize(width - height - 2, height);
        _text.setOffset(height+2, 0);
    }

    public HorizontalTextAlignment getHorizontalAlignment() {
        return _text.getHorizontalAlignment();
    }
    public void setHorizontalAlignment(HorizontalTextAlignment alignment) {
        _text.setHorizontalAlignment(alignment);
    }
    public VerticalTextAlignment getVerticalAlignment() {
        return _text.getVerticalAlignment();
    }
    public void setVerticalAlignment(VerticalTextAlignment alignment) {
        _text.setVerticalAlignment(alignment);
    }
    public TextOverflowMode getOverflowMode() {
        return _text.getOverflowMode();
    }
    public void setOverflowMode(TextOverflowMode mode) {
        _text.setOverflowMode(mode);
    }


    public void setColour(int argb) {
        _text.setColour(argb);
    }
    public void setColour(int red, int green, int blue, int alpha) {
        _text.setColour(red, green, blue, alpha);
    }
    public int getColour() {
        return _text.getColour();
    }

    public void setText(String text) {
        _text.setText(text);
    }
    public String getText() {
        return _text.getText();
    }
}