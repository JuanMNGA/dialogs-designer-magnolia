package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.CodeFieldDefinition;

public class DraggableCodeField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 1876080624209267298L;
	
	private static final String magnoliaType = "code";
	
	private String fieldId;
	private CodeFieldDefinition definition;
	private FieldType type = FieldType.CODE;
	private String tableId;
	
	public DraggableCodeField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		this.definition = new CodeFieldDefinition();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}

	public CodeFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(CodeFieldDefinition definition) {
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
