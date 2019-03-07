package com.magnolia.rd.dialogs.designer.layouts;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class LayoutCreator {
	
	public LayoutCreator() {
		
	}
	
	public HorizontalLayout createMainLayout() {
		HorizontalLayout tmp = new HorizontalLayout();
		tmp.setStyleName(".layout-visible");
		return tmp;
	}
	
	public VerticalLayout createFieldsLayout() {
		VerticalLayout tmp = new VerticalLayout();
		tmp.setStyleName(".layout-visible");
		return tmp;
	}
	
	public VerticalLayout createPropertiesLayout() {
		VerticalLayout tmp = new VerticalLayout();
		tmp.setStyleName(".layout-visible");
		return tmp;
	}
	
	public VerticalLayout createGridLayout() {
		VerticalLayout tmp = new VerticalLayout();
		tmp.setStyleName(".layout-visible");
		return tmp;
	}

}
