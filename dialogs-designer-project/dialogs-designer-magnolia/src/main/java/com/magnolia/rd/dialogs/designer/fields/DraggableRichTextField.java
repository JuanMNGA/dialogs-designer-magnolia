package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.field.RichTextFieldDefinition;

public class DraggableRichTextField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 2468407864516527394L;
	
	public static final String CLASS_IMAGE = "dd_rich_text";
	
	private RichTextFieldDefinition definition;
	
	private FieldType type = FieldType.RICHTEXT;
	
	private String tableId;
	
	public DraggableRichTextField(String text) {
		super(text);
		definition = new RichTextFieldDefinition();
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

	public RichTextFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(RichTextFieldDefinition definition) {
		this.definition = definition;
	}
	
	public FieldType getType() {
		return type;
	}

	@Override
	public String getTableId() {
		return tableId;
	}

	@Override
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

}
