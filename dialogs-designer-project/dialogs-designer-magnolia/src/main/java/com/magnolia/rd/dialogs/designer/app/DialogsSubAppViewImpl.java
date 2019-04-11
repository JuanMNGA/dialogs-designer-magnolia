package com.magnolia.rd.dialogs.designer.app;

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

import com.magnolia.rd.dialogs.designer.utils.CommandUtils;
import com.magnolia.rd.dialogs.designer.utils.LayoutUtil;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import info.magnolia.cms.beans.config.MIMEMapping;
import info.magnolia.commands.CommandsManager;
import info.magnolia.commands.ExportJcrNodeToYamlCommand;
import info.magnolia.context.MgnlContext;
import info.magnolia.importexport.DataTransporter;
import info.magnolia.jcr.util.NodeNameHelper;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.framework.util.TempFileStreamResource;

public class DialogsSubAppViewImpl implements DialogsSubAppView {

	private static final long serialVersionUID = 1246088245986891927L;
	
	private LayoutUtil layoutUtil;
	private Listener listener;
	
	@Inject
	private CommandsManager commandsManager;
	
	// Layouts
	private HorizontalLayout mainLayout = new HorizontalLayout();
	private VerticalLayout fieldsLayout;
	private VerticalLayout dialogLayout;
	private VerticalLayout propertiesLayout;

    /**
     * Default Constructor. Call the method to initialize the layouts
     */
    public DialogsSubAppViewImpl() {
    	super();
    	layoutUtil = Components.getComponent(LayoutUtil.class);
    	initLayouts();
    }
    
    /**
     * Initialize the layouts with the default configuration
     */
    private void initLayouts() {
    	mainLayout = new HorizontalLayout();
    	mainLayout.addStyleName("dd_main_layout");
    	
    	// Create the layouts
    	fieldsLayout = layoutUtil.createFieldsLayout();
    	propertiesLayout = layoutUtil.createPropertiesLayout();
    	dialogLayout = layoutUtil.createDialogLayout(propertiesLayout);
    	
    	// Add the layouts to mainLayout
    	mainLayout.addComponent(fieldsLayout);
    	mainLayout.addComponent(dialogLayout);
    	mainLayout.addComponent(propertiesLayout);
    	
    	Node rootNode = SessionUtil.getNode("config", "/modules/dialogs-designer-magnolia");
    	try {
    		Node dialogFolder = NodeUtil.createPath(rootNode, "dialogs", "mgnl:content");
			Node dialog = dialogFolder.addNode(Components.getComponent(NodeNameHelper.class).getUniqueName(dialogFolder, "pruebadialogo"), "mgnl:contentNode");
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
			
			commandsManager = Components.getComponent(CommandsManager.class);
			ExportJcrNodeToYamlCommand command = (ExportJcrNodeToYamlCommand)commandsManager.getCommand("default", "exportYaml");
			TempFileStreamResource tempFileStreamResource = new TempFileStreamResource();
			tempFileStreamResource.setTempFileName("asdasdasd.yaml");
			try {
				command.setOutputStream(tempFileStreamResource.getTempFileOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			Map<String, Object> commandsParams = new HashMap<>();
			commandsParams.put("repository", "config");
			commandsParams.put("path", dialog.getPath());
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
    	
    	
    }

    @Override
    public Component asVaadinComponent() {
        return mainLayout;
    }
    
    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

	@Override
	public void setTheme(String themeName) {
		String stylename = String.format("app-%s", themeName);
        final String themeUrl = String.format("../%s/css/styles.css", themeName);
        final String fontAwesomeUrl = String.format("../%s/css/font-awesome.css", themeName);
        

        final Component vaadinComponent = asVaadinComponent();
        vaadinComponent.addStyleName(stylename);
        final ThemeResource res = new ThemeResource(themeUrl);
        final ThemeResource fontAwesome = new ThemeResource(fontAwesomeUrl);

        if (vaadinComponent.isAttached()) {
            Page.getCurrent().getStyles().add(res);
            Page.getCurrent().getStyles().add(fontAwesome);
        } else {
            vaadinComponent.addAttachListener((AttachListener) event -> {Page.getCurrent().getStyles().add(res); Page.getCurrent().getStyles().add(fontAwesome);});
        }
	}

}