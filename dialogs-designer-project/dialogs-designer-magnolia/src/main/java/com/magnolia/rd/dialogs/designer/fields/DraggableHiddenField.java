package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.HiddenFieldDefinition;

public class DraggableHiddenField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 1876080624209267298L;
	
	private static final String magnoliaType = "hidden";
	
	private String fieldId;
	private HiddenFieldDefinition definition;
	private FieldType type = FieldType.HIDDEN;
	private String tableId;
	
	public DraggableHiddenField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		this.definition = new HiddenFieldDefinition();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}

	public HiddenFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(HiddenFieldDefinition definition) {
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
