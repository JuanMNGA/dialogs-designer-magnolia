package com.magnolia.rd.dialogs.designer.utils;

import java.util.Optional;

import javax.inject.Inject;

import com.magnolia.rd.dialogs.designer.constants.LayoutConstants;
import com.magnolia.rd.dialogs.designer.fields.DraggableField;
import com.magnolia.rd.dialogs.designer.fields.DraggableTextField;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;

import info.magnolia.i18nsystem.SimpleTranslator;

public class LayoutUtilImpl implements LayoutUtil {

	@Inject
	private SimpleTranslator i18n;

	@Override
	public VerticalLayout createFieldsLayout() {
		
		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("dd_fields_layout");

		// Text Field
		DraggableTextField draggableText = new DraggableTextField("Text Field");
		DragSourceExtension<Label> dragTextSource = new DragSourceExtension<>(draggableText);
		dragTextSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(draggableText);

		// Rich Text Field
		DraggableTextField draggableRich = new DraggableTextField("Rich Text Field");
		DragSourceExtension<Label> dragRichSource = new DragSourceExtension<>(draggableRich);
		dragRichSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(draggableRich);

		return vl;
	}

	@Override
	public VerticalLayout createDialogLayout() {
		
		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("dd_dialog_layout");

		// make the layout accept drops
		DropTargetExtension<VerticalLayout> dropTarget = new DropTargetExtension<>(vl);
		
		// the drop effect must match the allowed effect in the drag source for a
		// successful drop
		dropTarget.setDropEffect(DropEffect.MOVE);

		// catch the drops
		dropTarget.addDropListener(event -> {
			// if the drag source is in the same UI as the target
			Optional<AbstractComponent> dragSource = event.getDragSourceComponent();
			if (dragSource.isPresent() && dragSource.get() instanceof DraggableField) {
				HorizontalLayout hl = new HorizontalLayout();
				hl.addComponent(new Label(((DraggableField) dragSource.get()).getLabelText()));
				hl.addComponent(addRemoveButton(vl, hl)); // Remove
				hl.addComponent(addMoveAboveButton(vl, hl)); // Move above
				hl.addComponent(addMoveBelowButton(vl, hl)); // Move below

				vl.addComponent(hl);
			}
		});

		return vl;
	}
	
	@Override
	public VerticalLayout createPropertiesLayout() {
		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("dd_properties_layout");

		return vl;
	}

	private HorizontalLayout addRemoveButton(VerticalLayout containerLayout, HorizontalLayout component) {
		HorizontalLayout removeLayout = new HorizontalLayout();
		Label label = new Label("X");
		removeLayout.addComponent(label);
		removeLayout.addLayoutClickListener(new LayoutClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				containerLayout.removeComponent(component);
			}
		});
		
		return removeLayout;
	}
	
	private HorizontalLayout addMoveAboveButton(VerticalLayout containerLayout, HorizontalLayout component) {
		HorizontalLayout moveLayout = new HorizontalLayout();
		Label label = new Label("<");
		moveLayout.addComponent(label);
		moveLayout.addLayoutClickListener(new LayoutClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				int index = containerLayout.getComponentIndex(component);
				if(index > 0)
					containerLayout.addComponent(component, index-1);
			}
		});
		
		return moveLayout;
	}
	
	private HorizontalLayout addMoveBelowButton(VerticalLayout containerLayout, HorizontalLayout component) {
		HorizontalLayout moveLayout = new HorizontalLayout();
		Label label = new Label(">");
		moveLayout.addComponent(label);
		moveLayout.addLayoutClickListener(new LayoutClickListener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				int index = containerLayout.getComponentIndex(component);
				if(index < containerLayout.getComponentCount()-1)
					containerLayout.addComponent(component, index+2);
			}
		});
		
		return moveLayout;
	}
	
}
