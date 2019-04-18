package com.magnolia.rd.dialogs.designer.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.commons.io.FilenameUtils;

import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.VerticalLayout;

import info.magnolia.cms.beans.config.MIMEMapping;
import info.magnolia.commands.CommandsManager;
import info.magnolia.commands.ExportJcrNodeToYamlCommand;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeNameHelper;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.objectfactory.Components;
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
	public Node generateDialogNode(VerticalLayout layout) {
		Node rootNode = SessionUtil.getNode("config", "/modules/dialogs-designer-magnolia");
		try {
			Node dialogFolder = NodeUtil.createPath(rootNode, "dialogs", "mgnl:content");
			Node dialog = dialogFolder.addNode(
					Components.getComponent(NodeNameHelper.class).getUniqueName(dialogFolder, "pruebadialogo"),
					"mgnl:contentNode");
			dialog.setProperty("modalityLevel", "light");
			Node formNode = NodeUtil.createPath(dialog, "form", "mgnl:contentNode");
			Node tabsNode = NodeUtil.createPath(formNode, "tabs", "mgnl:contentNode");
			Node tabMain = NodeUtil.createPath(tabsNode, "tabMain", "mgnl:contentNode");
			tabMain.setProperty("name", "tabMain");
			Node actionsNode = NodeUtil.createPath(dialog, "actions", "mgnl:contentNode");
			Node commitNode = NodeUtil.createPath(actionsNode, "commit", "mgnl:contentNode");
			commitNode.setProperty("class", "info.magnolia.ui.dialog.action.SaveDialogActionDefinition");
			Node cancelNode = NodeUtil.createPath(actionsNode, "cancel", "mgnl:contentNode");
			cancelNode.setProperty("class", "info.magnolia.ui.dialog.action.CancelDialogActionDefinition");

			dialogFolder.getSession().save();
			dialog.getSession().save();

			MgnlContext.getJCRSession("config").save();

			return dialog;
		} catch (ItemExistsException e) {
			e.printStackTrace();
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchNodeTypeException e) {
			e.printStackTrace();
		} catch (LockException e) {
			e.printStackTrace();
		} catch (VersionException e) {
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
