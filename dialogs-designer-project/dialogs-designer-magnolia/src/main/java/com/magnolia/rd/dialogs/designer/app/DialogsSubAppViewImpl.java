package com.magnolia.rd.dialogs.designer.app;

import com.magnolia.rd.dialogs.designer.utils.LayoutUtil;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

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
    	dialogLayout = layoutUtil.createDialogLayout();
    	propertiesLayout = layoutUtil.createPropertiesLayout();
    	
    	// Add the layouts to mainLayout
    	mainLayout.addComponent(fieldsLayout);
    	mainLayout.addComponent(dialogLayout);
    	mainLayout.addComponent(propertiesLayout);
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
        final String themeUrl = String.format("../%s/styles.css", themeName);

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