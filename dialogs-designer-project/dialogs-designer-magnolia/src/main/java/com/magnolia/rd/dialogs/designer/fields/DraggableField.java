package com.magnolia.rd.dialogs.designer.fields;

import info.magnolia.ui.form.field.definition.ConfiguredFieldDefinition;

public interface DraggableField {

	void onDrag();
	void onDrop();
	String getLabelText();
	
	ConfiguredFieldDefinition getDefinition();
	
	public FieldType getType();
	
	public String getTableId();
	
	public void setTableId(String tableId);
	
}
