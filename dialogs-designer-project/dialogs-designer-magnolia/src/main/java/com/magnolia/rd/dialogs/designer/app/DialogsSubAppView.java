package com.magnolia.rd.dialogs.designer.app;

import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.api.view.View;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;

public interface DialogsSubAppView extends View {
	
	void setTheme(String theme);
	void setSubAppContext(SubAppContext subAppContext);
	void setContentConnector(ContentConnector contentConnector);
	void setListener(Listener listener);
	void initLayouts();
	
	public interface Listener {
        
    }

}
