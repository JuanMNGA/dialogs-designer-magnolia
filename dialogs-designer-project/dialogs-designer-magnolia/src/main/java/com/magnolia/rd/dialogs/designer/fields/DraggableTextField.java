package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.TextFieldDefinition;

public class DraggableTextField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 1876080624209267298L;
	
	private static final String magnoliaType = "text";
	
	private String fieldId;
	private TextFieldDefinition definition;
	private FieldType type = FieldType.TEXT;
	private String tableId;
	
	public DraggableTextField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		this.definition = new TextFieldDefinition();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}

	public TextFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(TextFieldDefinition definition) {
		this.definition = definition;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	
	public FieldType getDraggableType() {
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
