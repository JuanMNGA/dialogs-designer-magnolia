package com.magnolia.rd.dialogs.designer.utils;

import javax.jcr.Node;

import com.google.inject.ImplementedBy;
import com.vaadin.server.Resource;
import com.vaadin.ui.VerticalLayout;

public interface ExportUtil {

	/**
	 * Export one node to yaml
	 * @param dialogNode Node to be exported
	 * @return Yaml resource
	 */
	Resource exportToYaml(String dialogPath);
	
	
	/**
	 * Generates a node of type dialog from the property layout
	 * @param layout Properties layout
	 * @return A dialog node
	 */
	Node generateDialogNode(VerticalLayout dialogLayout, VerticalLayout propertiesLayout, boolean inPreview);
	
}
