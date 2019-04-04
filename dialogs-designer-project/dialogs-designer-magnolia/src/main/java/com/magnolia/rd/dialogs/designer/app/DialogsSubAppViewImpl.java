package com.magnolia.rd.dialogs.designer.app;

import javax.inject.Inject;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import com.magnolia.rd.dialogs.designer.utils.CommandUtils;
import com.magnolia.rd.dialogs.designer.utils.LayoutUtil;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import info.magnolia.jcr.util.SessionUtil;
import info.magnolia.objectfactory.Components;

public class DialogsSubAppViewImpl implements DialogsSubAppView {

	private static final long serialVersionUID = 1246088245986891927L;
	
	private LayoutUtil layoutUtil;
	private Listener listener;
	
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
    		Node prueba = rootNode.addNode("dialogs", "mgnl:content");
			Node prueba2 = prueba.addNode("prueba", "mgnl:contentNode");
			prueba2.setProperty("algo", "otro");
			prueba.getSession().save();
			prueba2.getSession().save();
			Components.newInstance(CommandUtils.class).executeCommand("exportYaml", "default", "config", prueba2.getPath());
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

        final Component vaadinComponent = asVaadinComponent();
        vaadinComponent.addStyleName(stylename);
        final ThemeResource res = new ThemeResource(themeUrl);

        if (vaadinComponent.isAttached()) {
            Page.getCurrent().getStyles().add(res);
        } else {
            vaadinComponent.addAttachListener((AttachListener) event -> Page.getCurrent().getStyles().add(res));
        }
	}

}