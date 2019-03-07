package com.magnolia.rd.dialogs.designer.app;

import javax.inject.Inject;

import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.magnolia.rd.dialogs.designer.utils.TextField;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import info.magnolia.ui.api.view.View;
import info.magnolia.ui.form.field.definition.TextFieldDefinition;;

public class DialogsSubAppViewImpl implements DialogsSubAppView {

	private static final long serialVersionUID = 1246088245986891927L;
	
	private Listener listener;
	private HorizontalLayout mainLayout = new HorizontalLayout();

    @Inject
    public DialogsSubAppViewImpl() {
        Label label = new Label("asd");
        label.setStyleName("layout-visible");
        TextField text = new TextField(new TextFieldDefinition());
        ((TextFieldDefinition)text.getDefinitionObject()).setRows(4);
        System.out.println(((TextFieldDefinition)text.getDefinitionObject()).getRows());
        mainLayout.addComponent(label);
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
	public String addSubAppView(View arg0, String arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeSubAppView(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getActiveSubAppView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getSubAppViewContainer(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActiveSubAppView(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAppLogo(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAppLogo(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAppName(String arg0) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void updateCaption(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}