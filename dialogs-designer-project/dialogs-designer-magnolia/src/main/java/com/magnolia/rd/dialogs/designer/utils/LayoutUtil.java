package com.magnolia.rd.dialogs.designer.utils;

import com.vaadin.ui.VerticalLayout;

public interface LayoutUtil {

	/**
	 * Creates the layout with the available fields to drag
	 * 
	 * @return A vertical layout with the fields
	 */
	VerticalLayout createFieldsLayout();

	/**
	 * Create the central layer, where you can drag the fields, move them, select
	 * them and delete them
	 * 
	 * @param propertiesLayout
	 * @return A vertical layout with the properties
	 */
	VerticalLayout createDialogLayout(VerticalLayout propertiesLayout);

	/**
	 * Creates the layer where the properties of each field will be stored. At the
	 * beginning it will be empty and will change depending on the selected field
	 * 
	 * @return A vertical layout where properties will be shown
	 */
	VerticalLayout createPropertiesLayout();

}
