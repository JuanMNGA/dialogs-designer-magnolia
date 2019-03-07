package com.magnolia.rd.dialogs.designer.app;

import info.magnolia.ui.api.view.View;

public interface DialogsSubAppView extends View {
	
	void setTheme(String theme);
	
	void setListener(Listener listener);
	
	public interface Listener {
        
    }

}
