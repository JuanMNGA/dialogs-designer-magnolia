package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.LinkFieldDefinition;

public class DraggableLinkField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 1876080624209267298L;
	
	private static final String magnoliaType = "link";
	
	private String fieldId;
	private LinkFieldDefinition definition;
	private FieldType type = FieldType.LINK;
	private String tableId;
	
	public DraggableLinkField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		this.definition = new LinkFieldDefinition();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}

	public LinkFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(LinkFieldDefinition definition) {
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
