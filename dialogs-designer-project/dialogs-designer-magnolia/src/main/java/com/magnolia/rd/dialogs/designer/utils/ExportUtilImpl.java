package com.magnolia.rd.dialogs.designer.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import com.magnolia.rd.dialogs.designer.constants.DialogConstants;
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
		tempFileStreamResource.setTempFileName("asdasdasd.yaml");
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
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			commandsManager.executeCommand(command, commandsParams);
		} catch (Exception e) {
			e.printStackTrace();
		}

		tempFileStreamResource.setFilename("asdasdasd.yaml");
		tempFileStreamResource.setMIMEType(MIMEMapping.getMIMEType(FilenameUtils.getExtension("asdasdasd.yaml")));
		Page.getCurrent().open(tempFileStreamResource, "", true);

		return tempFileStreamResource;
	}

	@Override
	public Node generateDialogNode(VerticalLayout propertiesLayout) {

		Node rootNode = SessionUtil.getNode(RepositoryConstants.CONFIG, DialogConstants.MODULE_PATH);

		try {
			Node dialogFolder = NodeUtil.createPath(rootNode, "dialogs", NodeTypes.Content.NAME);
			// Dialog node
			Node dialog = dialogFolder.addNode(
					Components.getComponent(NodeNameHelper.class).getUniqueName(dialogFolder, "dialog"),
					NodeTypes.ContentNode.NAME);

			// Actions Node
			Node actionsNode = NodeUtil.createPath(dialog, "actions", NodeTypes.ContentNode.NAME);
			Node commitNode = NodeUtil.createPath(actionsNode, "commit", NodeTypes.ContentNode.NAME);
			commitNode.setProperty("class", "info.magnolia.ui.dialog.action.SaveDialogActionDefinition");
			Node cancelNode = NodeUtil.createPath(actionsNode, "cancel", NodeTypes.ContentNode.NAME);
			cancelNode.setProperty("class", "info.magnolia.ui.dialog.action.CancelDialogActionDefinition");

			// Form and tabs Node
			Node formNode = NodeUtil.createPath(dialog, "form", NodeTypes.ContentNode.NAME);
			Node tabsNode = NodeUtil.createPath(formNode, "tabs", NodeTypes.ContentNode.NAME);
			Node tabMainNode = NodeUtil.createPath(tabsNode, "tabMain", NodeTypes.ContentNode.NAME);
			tabMainNode.setProperty("name", "tabMain");
			
			// Fields Node
			Node fields = NodeUtil.createPath(tabMainNode, "fields", NodeTypes.ContentNode.NAME);
			generateFieldsNode(fields, propertiesLayout);

			dialog.getSession().save();

			return dialog;
		} catch (RepositoryException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private void generateFieldsNode(Node fields, VerticalLayout layout) throws AccessDeniedException, PathNotFoundException, RepositoryException {
		
		// First iterate over all the tables with  diferent field properties
		for(int i=0; i<layout.getComponentCount(); i++) {
			
			// Creates one node for every table
			VerticalLayout vl = (VerticalLayout) layout.getComponent(i);
			HorizontalLayout hl = (HorizontalLayout) vl.getComponent(0);
			VerticalLayout table = (VerticalLayout) hl.getComponent(0);
			String fieldName = searchFieldName(table);
			Node field = NodeUtil.createPath(fields, Path.getUniqueLabel(fields, fieldName), NodeTypes.ContentNode.NAME);
			
			// Then iterate over all the properties in the table
			for(int j=0; j<table.getComponentCount(); j++) {
				
				Component component = table.getComponent(j);
				String value = getComponentValue(component);
				if(StringUtils.isNotEmpty(value) && component.getCaption() != null)
					field.setProperty(component.getCaption(), value);
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
