package com.magnolia.rd.dialogs.designer.app;

import javax.inject.Inject;

import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.framework.app.BaseSubApp;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;

public class DialogsSubApp extends BaseSubApp<DialogsSubAppView> implements DialogsSubAppView.Listener {

    @Inject
    public DialogsSubApp(final SubAppContext subAppContext, DialogsSubAppView view, ContentConnector contentConnector) {
        super(subAppContext, view);
        view.setContentConnector(contentConnector);
        view.setSubAppContext(subAppContext);
        view.initLayouts();
        view.setTheme(subAppContext.getAppContext().getAppDescriptor().getTheme());
    }

}
