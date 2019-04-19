package com.magnolia.rd.dialogs.designer.fields;

import info.magnolia.ui.form.field.definition.ConfiguredFieldDefinition;

public interface DraggableField {

	String getLabelText();
	
	ConfiguredFieldDefinition getDefinition();
	
	FieldType getDraggableType();
	
	String getMagnoliaType();
	
	String getTableId();
	
	void setTableId(String tableId);
	
}
