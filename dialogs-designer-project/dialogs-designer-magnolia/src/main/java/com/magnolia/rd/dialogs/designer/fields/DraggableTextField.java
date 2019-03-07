package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.field.TextFieldDefinition;

public class DraggableTextField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 1876080624209267298L;
	
	public static final String CLASS_IMAGE = "dd_text";
	
	private TextFieldDefinition definition;
	
	public DraggableTextField(String text) {
		super(text);
		this.definition = new TextFieldDefinition();
	}
	
	@Override
	public void onDrag() {
	}

	@Override
	public void onDrop() {
	}
	
	@Override
	public String getLabelText() {
		return getValue();
	}

	public TextFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(TextFieldDefinition definition) {
		this.definition = definition;
	}

}
