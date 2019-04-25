package com.magnolia.rd.dialogs.designer.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.magnolia.rd.dialogs.designer.constants.DialogConstants;
import com.magnolia.rd.dialogs.designer.fields.DraggableField;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import info.magnolia.cms.beans.config.MIMEMapping;
import info.magnolia.cms.core.Path;
import info.magnolia.cms.security.AccessDeniedException;
import info.magnolia.commands.CommandsManager;
import info.magnolia.commands.ExportJcrNodeToYamlCommand;
import info.magnolia.jcr.util.NodeNameHelper;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.ui.framework.util.TempFileStreamResource;

public class ExportUtilImpl implements ExportUtil {

	@Inject
	private CommandsManager commandsManager;

	@Override
	public Resource exportToYaml(String dialogPath) {
		commandsManager = Components.getComponent(CommandsManager.class);
		ExportJcrNodeToYamlCommand command = (ExportJcrNodeToYamlCommand) commandsManager.getCommand("default",
				"exportYaml");
		TempFileStreamResource tempFileStreamResource = new TempFileStreamResource();
		String[] pathSplitted = dialogPath.split("/");
		String dialogName = pathSplitted[pathSplitted.length - 1] + ".yaml";
		tempFileStreamResource.setTempFileName(dialogName);
		try {
			command.setOutputStream(tempFileStreamResource.getTempFileOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> commandsParams = new HashMap<>();
		commandsParams.put("repository", "config");
		commandsParams.put("path", dialogPath);
		commandsParams.put("recursive", true);

		try {
			Thread.sleep(5000);
			commandsManager.executeCommand(command, commandsParams);
		} catch (Exception e) {
			e.printStackTrace();
		}

		tempFileStreamResource.setFilename(dialogName);
		tempFileStreamResource.setMIMEType(MIMEMapping.getMIMEType(FilenameUtils.getExtension(dialogName)));
		Page.getCurrent().open(tempFileStreamResource, "", true);

		return tempFileStreamResource;
	}

	@Override
	public Node generateDialogNode(VerticalLayout dialogLayout, VerticalLayout propertiesLayout, boolean inPreview) {

		Node rootNode = SessionUtil.getNode(RepositoryConstants.CONFIG, DialogConstants.MODULE_PATH);

		try {
			Node dialogFolder = NodeUtil.createPath(rootNode, "dialogs", NodeTypes.Content.NAME);
			// Dialog node
			Node dialog = NodeUtil.createPath(dialogFolder, "dialog",NodeTypes.ContentNode.NAME); // .addNode(Components.getComponent(NodeNameHelper.class).getUniqueName(dialogFolder, "dialog"),NodeTypes.ContentNode.NAME);

			// Form and tabs Node
			Node formNode = NodeUtil.createPath(dialog, "form", NodeTypes.ContentNode.NAME);
			Node tabsNode = NodeUtil.createPath(formNode, "tabs", NodeTypes.ContentNode.NAME);
			Node tabMainNode = NodeUtil.createPath(tabsNode, "tabMain", NodeTypes.ContentNode.NAME);
			tabMainNode.setProperty("name", "tabMain");
			
			// Fields Node
			Node fields = NodeUtil.createPath(tabMainNode, "fields", NodeTypes.ContentNode.NAME);
			NodeIterator childNodes = fields.getNodes();
			while(childNodes.hasNext()) {
				Node toDelete = childNodes.nextNode();
				toDelete.remove();
			}
			generateFieldsNode(fields, dialogLayout, propertiesLayout);
			
			// Actions Node
			Node actionsNode = NodeUtil.createPath(dialog, "actions", NodeTypes.ContentNode.NAME);
			Node commitNode = NodeUtil.createPath(actionsNode, "commit", NodeTypes.ContentNode.NAME);
			if(!inPreview) {
				commitNode.setProperty("class", "info.magnolia.ui.dialog.action.SaveDialogActionDefinition");
			} else {
				commitNode.setProperty("class", "info.magnolia.ui.dialog.action.CancelDialogActionDefinition");
			}
			Node cancelNode = NodeUtil.createPath(actionsNode, "cancel", NodeTypes.ContentNode.NAME);
			cancelNode.setProperty("class", "info.magnolia.ui.dialog.action.CancelDialogActionDefinition");
			
			dialog.getSession().save();

			return dialog;
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private void generateFieldsNode(Node fields, VerticalLayout dialogLayout, VerticalLayout propertiesLayout) throws AccessDeniedException, PathNotFoundException, RepositoryException {
		
		for(int i = 1; i < dialogLayout.getComponentCount(); i++) {
			
			HorizontalLayout fieldHorizontalLayout = (HorizontalLayout) dialogLayout.getComponent(i);
			HorizontalLayout fieldLayout = (HorizontalLayout) fieldHorizontalLayout.getComponent(0);
			DraggableField currentField = (DraggableField) fieldLayout.getComponent(0);
			
			String currentTableId = currentField.getTableId();
			// Creates one node for every table
			Iterator<Component> allPropertyTables = propertiesLayout.iterator();
			boolean notFoundItem = true;
			while(allPropertyTables.hasNext() && notFoundItem) {
				VerticalLayout vl = (VerticalLayout) allPropertyTables.next();
				if(currentTableId.equalsIgnoreCase(vl.getId())) {
					notFoundItem = false;
					HorizontalLayout hl = (HorizontalLayout) vl.getComponent(0);
					VerticalLayout table = (VerticalLayout) hl.getComponent(0);
					String fieldName = searchFieldName(table).replaceAll("[\\W]+", "-");
					Node field = NodeUtil.createPath(fields, Path.getUniqueLabel(fields, fieldName), NodeTypes.ContentNode.NAME);
					
					// Then iterate over all the properties in the table
					for(int j=0; j<table.getComponentCount(); j++) {
						
						Component component = table.getComponent(j);
						String value = getComponentValue(component);
						if(StringUtils.isNotEmpty(value) && component.getCaption() != null) {
						
							if(component.getCaption().equals("contentPreviewDefinition")) { // Custom for link field
								Node previewNode = field.addNode("contentPreviewDefinition", NodeTypes.ContentNode.NAME);
								previewNode.setProperty("contentPreviewClass", value);
								
							} else if (component.getCaption().equals("identifierToPathConverter")) { // Custom for link field
								Node identifierNode = field.addNode("identifierToPathConverter", NodeTypes.ContentNode.NAME);
								identifierNode.setProperty("class", value);
								
							} else { // Is no a link field
								field.setProperty(component.getCaption(), value);
							}
							
						}
					}
				}
			}
		}
	}
	
	private String searchFieldName(VerticalLayout table) {
		for(int i=0; i<table.getComponentCount(); i++) {
			Component component = table.getComponent(i);
			if(component.getCaption() != null && "name".contentEquals(component.getCaption())){
				String name = getComponentValue(component);
				if(StringUtils.isNotEmpty(name))
					return name;
			}
		}
		
		return "field";
	}
	
	private String getComponentValue(Component component) {
		
		if(component instanceof TextField) {
			TextField text = (TextField) component;
			if(text.getValue() != null)
				return text.getValue();
		} else if (component instanceof CheckBox) {
			CheckBox check = (CheckBox) component;
			if(check.getValue() != null)
				return String.valueOf(check.getValue());
		} else if (component instanceof ComboBox) {
			ComboBox<String> combo = (ComboBox) component;
			if(combo.getSelectedItem() != null)
				return combo.getValue();
		}
		
		return null;
	}

}
