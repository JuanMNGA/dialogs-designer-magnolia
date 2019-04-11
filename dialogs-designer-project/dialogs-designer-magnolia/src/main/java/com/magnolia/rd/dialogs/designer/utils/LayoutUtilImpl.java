package com.magnolia.rd.dialogs.designer.utils;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang.ArrayUtils;

import com.magnolia.rd.dialogs.designer.fields.DraggableField;
import com.magnolia.rd.dialogs.designer.fields.DraggableRichTextField;
import com.magnolia.rd.dialogs.designer.fields.DraggableTextField;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.ui.field.ConfiguredFieldDefinition;

public class LayoutUtilImpl implements LayoutUtil {

	@Inject
	private SimpleTranslator i18n;
	
	private HorizontalLayout selected;

	@Override
	public VerticalLayout createFieldsLayout() {

		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("dd_fields_layout");

		// Text Field
		DraggableTextField draggableText = new DraggableTextField("<span>Text Field</span> <i class='fas fa-keyboard'></i>");
		HorizontalLayout textLayout = new HorizontalLayout(draggableText);
		DragSourceExtension<HorizontalLayout> dragTextSource = new DragSourceExtension<>(textLayout);
		dragTextSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(textLayout);

		// Rich Text Field
		DraggableRichTextField draggableRich = new DraggableRichTextField("<span>Rich Text Field</span> <i class='fas fa-keyboard'></i>");
		HorizontalLayout richTextLayout = new HorizontalLayout(draggableRich);
		DragSourceExtension<HorizontalLayout> dragRichSource = new DragSourceExtension<>(richTextLayout);
		dragRichSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(richTextLayout);

		return vl;
	}

	@Override
	public VerticalLayout createDialogLayout(VerticalLayout propertiesLayout) {

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
			if (dragSource.isPresent() && dragSource.get() instanceof HorizontalLayout) {
				HorizontalLayout hl = new HorizontalLayout();
				
				HorizontalLayout droppedLayout = ((HorizontalLayout) dragSource.get());
				DraggableField droppedField = (DraggableField) droppedLayout.getComponent(0);
				
				String tableId = getTableId(droppedField.getDefinition().getClass());
				hl.addComponent(addFieldLayout(propertiesLayout, droppedField, tableId));
				// Action buttons
				hl.addComponent(addRemoveButton(vl, hl, propertiesLayout, tableId)); // Remove
				hl.addComponent(addMoveAboveButton(vl, hl)); // Move above
				hl.addComponent(addMoveBelowButton(vl, hl)); // Move below
				vl.addComponent(hl);
				createNewPropertiesTable(propertiesLayout, tableId, droppedField);
			}
		});

		return vl;
	}
	
	private HorizontalLayout addFieldLayout(VerticalLayout propertiesLayout, DraggableField field, String tableId) {
		HorizontalLayout fieldLayout = new HorizontalLayout();
		DraggableField tmpField = null;
		switch (field.getType()) {
			case RICHTEXT:
				tmpField = new DraggableRichTextField(field.getLabelText());
				tmpField.setTableId(tableId);
				fieldLayout.addComponent((DraggableRichTextField)tmpField);
				break;
			default:
				tmpField = new DraggableTextField(field.getLabelText());
				tmpField.setTableId(tableId);
				fieldLayout.addComponent((DraggableTextField)tmpField);
				break;
		}
		fieldLayout.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				hideAllPropertiesLayout(propertiesLayout);
				Iterator<Component> iterator = propertiesLayout.iterator();
				if(selected != null) {
					selected.removeStyleName("prueba");
				}
				selected = (HorizontalLayout)((HorizontalLayout) event.getSource()).getParent();
				selected.setStyleName("prueba");
				while(iterator.hasNext()) {
					Component tmp = iterator.next();
					if(tmp.getId().equalsIgnoreCase(tableId)){
						tmp.setVisible(true);
					}
				}
			}
		});
		return fieldLayout;
	}

	@Override
	public VerticalLayout createPropertiesLayout() {
		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("dd_properties_layout");

		return vl;
	}

	private HorizontalLayout addRemoveButton(VerticalLayout containerLayout, HorizontalLayout component, VerticalLayout propertiesLayout, String tableId) {
		HorizontalLayout removeLayout = new HorizontalLayout();
		Label label = new Label("X");
		removeLayout.addComponent(label);
		removeLayout.addLayoutClickListener(new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				Iterator<Component> iterator = propertiesLayout.iterator();
				while(iterator.hasNext()) {
					Component tmp = iterator.next();
					if(tmp.getId().equalsIgnoreCase(tableId)){
						propertiesLayout.removeComponent(tmp);
						break;
					}
				}
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
				if (index > 0)
					containerLayout.addComponent(component, index - 1);
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
				if (index < containerLayout.getComponentCount() - 1)
					containerLayout.addComponent(component, index + 2);
			}
		});

		return moveLayout;
	}
	
	private void hideAllPropertiesLayout(VerticalLayout propertiesLayout) {
		Iterator<Component> iterator = propertiesLayout.iterator();
		while(iterator.hasNext()) {
			iterator.next().setVisible(false);
		}
		
	}
	
	private void createNewPropertiesTable(VerticalLayout propertiesLayout, String tableId, DraggableField draggableField) {
		hideAllPropertiesLayout(propertiesLayout);
		try {
			VerticalLayout propertyValue = new VerticalLayout();
			VerticalLayout propertyTable = new VerticalLayout();
			HorizontalLayout propertyRow = new HorizontalLayout();
			Class<? extends ConfiguredFieldDefinition> classDefinition = draggableField.getDefinition().getClass();
			Field[] commonFields = ConfiguredFieldDefinition.class.getDeclaredFields();
			Field[] fields = (Field[])ArrayUtils.addAll(commonFields, classDefinition.getDeclaredFields());
			for (int i = 0; i < fields.length; ++i) {
				propertyValue.addComponent(getComponentByFieldType(fields[i], draggableField));
				propertyRow.addComponent(propertyValue);
				propertyTable.addComponent(propertyRow);
			}
			propertyTable.setId(tableId);
			propertiesLayout.addComponent(propertyTable);
		} catch (Exception e) {}
	}
	
	private String getTableId(Class<? extends ConfiguredFieldDefinition> definition) {
		return definition.getSimpleName() + "_" + Calendar.getInstance().getTimeInMillis();
	}
	
	private Component getComponentByFieldType(Field currentField, DraggableField draggableField) {
		Class fieldType = currentField.getType();
		switch(fieldType.getSimpleName()) {
		case "Boolean":
		case "boolean":
			try {
				return new CheckBox(currentField.getName(), currentField.getBoolean(draggableField));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				return new CheckBox(currentField.getName());
			}
		default:
			return new TextField(currentField.getName());
		}
	}

}
