package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.PasswordFieldDefinition;

public class DraggablePasswordField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 1876080624209267298L;
	
	private static final String magnoliaType = "password";
	
	private String fieldId;
	private PasswordFieldDefinition definition;
	private FieldType type = FieldType.PASSWORD;
	private String tableId;
	
	public DraggablePasswordField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		this.definition = new PasswordFieldDefinition ();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}

	public PasswordFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(PasswordFieldDefinition definition) {
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
