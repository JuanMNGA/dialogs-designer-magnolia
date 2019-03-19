package com.magnolia.rd.dialogs.designer.fields;

import info.magnolia.ui.field.ConfiguredFieldDefinition;

public interface DraggableField {

	void onDrag();
	void onDrop();
	String getLabelText();
	
	ConfiguredFieldDefinition<String> getDefinition();
	
	public FieldType getType();
	
	public String getTableId();
	
	public void setTableId(String tableId);
	
}
