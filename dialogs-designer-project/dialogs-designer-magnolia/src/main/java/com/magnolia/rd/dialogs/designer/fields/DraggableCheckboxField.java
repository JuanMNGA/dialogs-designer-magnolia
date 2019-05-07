package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.CheckboxFieldDefinition;

public class DraggableCheckboxField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 1876080624209267298L;
	
	private static final String magnoliaType = "checkbox";
	
	private String fieldId;
	private CheckboxFieldDefinition definition;
	private FieldType type = FieldType.CHECKBOX;
	private String tableId;
	
	public DraggableCheckboxField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		this.definition = new CheckboxFieldDefinition ();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}

	public CheckboxFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(CheckboxFieldDefinition definition) {
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
