package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.*;
import org.darkstorm.minecraft.gui.listener.*;

public class BasicButton extends AbstractComponent implements Button {
	protected String text = "";
	protected ButtonGroup group;

	public BasicButton() {
	}

	public BasicButton(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void press() {
		for(ComponentListener listener : getListeners()) {
			if(listener instanceof ButtonListener) {
				try {
					((ButtonListener) listener).onButtonPress(this);
				} catch(Exception exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	@Override
	public void addButtonListener(ButtonListener listener) {
		addListener(listener);
	}

	@Override
	public void removeButtonListener(ButtonListener listener) {
		removeListener(listener);
	}

	@Override
	public ButtonGroup getGroup() {
		return group;
	}

	@Override
	public void setGroup(ButtonGroup group) {
		this.group = group;
	}
}
