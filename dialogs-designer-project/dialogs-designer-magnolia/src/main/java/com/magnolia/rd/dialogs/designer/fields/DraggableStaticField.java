package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.StaticFieldDefinition;

public class DraggableStaticField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 1876080624209267298L;
	
	private static final String magnoliaType = "static";
	
	private String fieldId;
	private StaticFieldDefinition definition;
	private FieldType type = FieldType.STATIC;
	private String tableId;
	
	public DraggableStaticField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		this.definition = new StaticFieldDefinition ();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}

	public StaticFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(StaticFieldDefinition definition) {
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
