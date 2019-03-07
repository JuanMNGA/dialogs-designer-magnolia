package com.magnolia.rd.dialogs.designer.app;

import javax.inject.Inject;

import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.framework.app.BaseSubApp;

public class DialogsSubApp extends BaseSubApp<DialogsSubAppView> implements DialogsSubAppView.Listener {

	@Inject
	public DialogsSubApp(final SubAppContext subAppContext, DialogsSubAppView view) {
		super(subAppContext, view);
		view.setTheme(subAppContext.getAppContext().getAppDescriptor().getTheme());
	}

	@Override
	public void onFocus(String instanceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose(String instanceId) {
		// TODO Auto-generated method stub
		
	}

}
