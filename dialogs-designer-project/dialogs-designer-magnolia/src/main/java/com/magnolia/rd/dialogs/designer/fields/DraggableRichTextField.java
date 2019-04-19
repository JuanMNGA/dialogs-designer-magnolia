package com.magnolia.rd.dialogs.designer.fields;

import com.vaadin.ui.Label;

import info.magnolia.ui.form.field.definition.RichTextFieldDefinition;

public class DraggableRichTextField extends Label implements DraggableField {
	
	private static final long serialVersionUID = 2468407864516527394L;
	
	private static final String magnoliaType = "richText";
	
	private RichTextFieldDefinition definition;
	private FieldType type = FieldType.RICHTEXT;
	private String tableId;
	
	public DraggableRichTextField(String text) {
		super.setCaption(text);
		super.setCaptionAsHtml(true);
		definition = new RichTextFieldDefinition();
	}
	
	@Override
	public String getMagnoliaType() {
		return magnoliaType;
	}
	
	@Override
	public String getLabelText() {
		return getCaption();
	}
	
	public RichTextFieldDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(RichTextFieldDefinition definition) {
		this.definition = definition;
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
