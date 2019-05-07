package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.BasicUploadFieldDefinition;

public class DraggableBasicUploadField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 1876080624209267298L;
	
	private static final String magnoliaType = "basicUpload";
	
	private String fieldId;
	private BasicUploadFieldDefinition definition;
	private FieldType type = FieldType.BASICUPLOAD;
	private String tableId;
	
	public DraggableBasicUploadField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		this.definition = new BasicUploadFieldDefinition ();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}

	public BasicUploadFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(BasicUploadFieldDefinition definition) {
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
