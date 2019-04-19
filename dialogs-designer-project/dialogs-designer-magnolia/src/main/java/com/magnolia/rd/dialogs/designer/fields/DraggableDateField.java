package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.DateFieldDefinition;

public class DraggableDateField extends Label implements DraggableField {
	
	private static final long serialVersionUID = -5814938430973961031L;
	
	private static final String magnoliaType = "Date";

	private String fieldId;
	private DateFieldDefinition definition;
	private FieldType type = FieldType.DATE;
	private String tableId;
	
	public DraggableDateField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		this.definition = new DateFieldDefinition();
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}

	public DateFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(DateFieldDefinition definition) {
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
