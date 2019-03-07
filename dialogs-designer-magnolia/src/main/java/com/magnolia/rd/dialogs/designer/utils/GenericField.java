package com.magnolia.rd.dialogs.designer.utils;

import info.magnolia.ui.form.field.definition.ConfiguredFieldDefinition;

public class GenericField {
	
	private String img;
	
	private ConfiguredFieldDefinition def;
	
	public GenericField(ConfiguredFieldDefinition def) {
		this.def = def;
	}
	
	public ConfiguredFieldDefinition getDefinitionObject() {
		return def;
	}

}
