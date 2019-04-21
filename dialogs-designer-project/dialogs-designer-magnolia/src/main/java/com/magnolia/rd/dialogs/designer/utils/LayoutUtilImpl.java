package com.magnolia.rd.dialogs.designer.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.ArrayUtils;

import com.magnolia.rd.dialogs.designer.constants.DialogConstants;
import com.magnolia.rd.dialogs.designer.fields.DraggableCodeField;
import com.magnolia.rd.dialogs.designer.fields.DraggableDateField;
import com.magnolia.rd.dialogs.designer.fields.DraggableField;
import com.magnolia.rd.dialogs.designer.fields.DraggableHiddenField;
import com.magnolia.rd.dialogs.designer.fields.DraggableLinkField;
import com.magnolia.rd.dialogs.designer.fields.DraggableRichTextField;
import com.magnolia.rd.dialogs.designer.fields.DraggableTextField;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.v7.data.Item;

import info.magnolia.event.EventBus;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.api.event.ContentChangedEvent;
import info.magnolia.ui.dialog.DialogPresenter;
import info.magnolia.ui.dialog.callback.DefaultEditorCallback;
import info.magnolia.ui.dialog.formdialog.FormDialogPresenter;
import info.magnolia.ui.dialog.formdialog.FormDialogPresenterFactory;
import info.magnolia.ui.form.EditorCallback;
import info.magnolia.ui.form.field.definition.ConfiguredFieldDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeAdapter;

public class LayoutUtilImpl implements LayoutUtil {

	@Inject
	private SimpleTranslator i18n;
	@Inject
	private ExportUtil exportUtil;

	private HorizontalLayout selected;

	private Window dialogWindow;
	private List<Component> actions;

	@Override
	public VerticalLayout createPropertiesLayout() {
		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("dd_properties_layout");

		return vl;
	}

	@Override
	public VerticalLayout createFieldsLayout() {

		VerticalLayout vl = new VerticalLayout();
		vl.addStyleName("dd_fields_layout");

		// Text Field
		DraggableTextField draggableText = new DraggableTextField(
				"<span>" + i18n.translate("dialogs-app.field.text", "") + "</span> <i class='fas fa-keyboard'></i>");
		HorizontalLayout textLayout = new HorizontalLayout(draggableText);
		DragSourceExtension<HorizontalLayout> dragTextSource = new DragSourceExtension<>(textLayout);
		dragTextSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(textLayout);

		// Rich Text Field
		DraggableRichTextField draggableRich = new DraggableRichTextField("<span>"
				+ i18n.translate("dialogs-app.field.rich-text", "") + "</span> <i class='fas fa-keyboard'></i>");
		HorizontalLayout richTextLayout = new HorizontalLayout(draggableRich);
		DragSourceExtension<HorizontalLayout> dragRichSource = new DragSourceExtension<>(richTextLayout);
		dragRichSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(richTextLayout);

		// Date Field
		DraggableDateField draggableDate = new DraggableDateField(
				"<span>" + i18n.translate("dialogs-app.field.date", "") + "</span> <i class='fas fa-keyboard'></i>");
		HorizontalLayout dateLayout = new HorizontalLayout(draggableDate);
		DragSourceExtension<HorizontalLayout> dragDateSource = new DragSourceExtension<>(dateLayout);
		dragDateSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(dateLayout);

		// Code Field
		DraggableCodeField draggableCode = new DraggableCodeField(
				"<span>" + i18n.translate("dialogs-app.field.code", "") + "</span> <i class='fas fa-keyboard'></i>");
		HorizontalLayout codeLayout = new HorizontalLayout(draggableCode);
		DragSourceExtension<HorizontalLayout> dragCodeSource = new DragSourceExtension<>(codeLayout);
		dragCodeSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(codeLayout);

		// Hidden Field
		DraggableHiddenField draggableHidden = new DraggableHiddenField(
				"<span>" + i18n.translate("dialogs-app.field.hidden", "") + "</span> <i class='fas fa-keyboard'></i>");
		HorizontalLayout hiddenLayout = new HorizontalLayout(draggableHidden);
		DragSourceExtension<HorizontalLayout> dragHiddenSource = new DragSourceExtension<>(hiddenLayout);
		dragHiddenSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(hiddenLayout);

		// Link Field
		DraggableLinkField draggableLink = new DraggableLinkField(
				"<span>" + i18n.translate("dialogs-app.field.link", "") + "</span> <i class='fas fa-keyboard'></i>");
		HorizontalLayout linkLayout = new HorizontalLayout(draggableLink);
		DragSourceExtension<HorizontalLayout> dragLinkSource = new DragSourceExtension<>(linkLayout);
		dragLinkSource.setEffectAllowed(EffectAllowed.MOVE);
		vl.addComponent(linkLayout);

		return vl;
	}

	@Override
	public VerticalLayout createDialogLayout(VerticalLayout propertiesLayout, SubAppContext subAppContext, ContentConnector contentConnector) {

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
				hl.addComponent(addMoveAboveButton(vl, hl)); // Move above
				hl.addComponent(addMoveBelowButton(vl, hl)); // Move below
				hl.addComponent(addRemoveButton(vl, hl, propertiesLayout, tableId)); // Remove
				vl.addComponent(hl);

				// Generates a property table for the field dropped
				createNewPropertiesTable(propertiesLayout, tableId, droppedField);
			}
		});

		// Buttons layout
		HorizontalLayout buttonsLayout = new HorizontalLayout();

		// Export button
		Button exportButton = new Button();
		exportButton.setCaption("Export Yaml <i class=\"fas fa-download\"></i>");
		exportButton.setCaptionAsHtml(true);

		// Export button click
		exportButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					Node dialogNode = exportUtil.generateDialogNode(propertiesLayout);
					exportUtil.exportToYaml(dialogNode.getPath());
				} catch (RepositoryException e) {
					e.printStackTrace();
				}
			}
		});
		buttonsLayout.addComponent(exportButton);

		// Preview button
		Button previewButton = new Button();
		previewButton.setCaption("Preview <i class=\"far fa-eye\"></i>");
		previewButton.setCaptionAsHtml(true);

		// Preview button click
		previewButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

				try {
					Node dialogNode = exportUtil.generateDialogNode(propertiesLayout);
					dialogNode.getName();
					
					String dialogId = "dialogs-designer-magnolia:" + dialogNode.getName();
					FormDialogPresenterFactory formDialogPresenterFactory = Components.getComponent(FormDialogPresenterFactory.class);
					final FormDialogPresenter formDialogPresenter = formDialogPresenterFactory.createFormDialogPresenter(dialogId);
					
					JcrNodeAdapter adapter = new JcrNodeAdapter(dialogNode);
					final Item item = contentConnector.getItem(adapter.getItemId());
		            formDialogPresenter.start(item, dialogId, subAppContext, createEditorCallback(formDialogPresenter));

				} catch (RepositoryException e) {
					e.printStackTrace();
				}

			}
		});
		buttonsLayout.addComponent(previewButton);

		vl.addComponent(buttonsLayout);

		return vl;
	}
	
	protected EditorCallback createEditorCallback(final DialogPresenter dialogPresenter) {
        return new DefaultEditorCallback(dialogPresenter) {
            @Override
            public void onSuccess(String actionName) {
               
            }
        };
    }

	private HorizontalLayout addFieldLayout(VerticalLayout propertiesLayout, DraggableField field, String tableId) {

		HorizontalLayout fieldLayout = new HorizontalLayout();
		DraggableField tmpField = null;

		switch (field.getDraggableType()) {
		case RICHTEXT:
			tmpField = new DraggableRichTextField(field.getLabelText());
			fieldLayout.addComponent((DraggableRichTextField) tmpField);
			break;
		case DATE:
			tmpField = new DraggableDateField(field.getLabelText());
			fieldLayout.addComponent((DraggableDateField) tmpField);
			break;
		case CODE:
			tmpField = new DraggableCodeField(field.getLabelText());
			fieldLayout.addComponent((DraggableCodeField) tmpField);
			break;
		case HIDDEN:
			tmpField = new DraggableHiddenField(field.getLabelText());
			fieldLayout.addComponent((DraggableHiddenField) tmpField);
			break;
		case LINK:
			tmpField = new DraggableLinkField(field.getLabelText());
			fieldLayout.addComponent((DraggableLinkField) tmpField);
			break;
		default:
			tmpField = new DraggableTextField(field.getLabelText());
			fieldLayout.addComponent((DraggableTextField) tmpField);
			break;
		}

		tmpField.setTableId(tableId);

		fieldLayout.addLayoutClickListener(new LayoutClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				hideAllPropertiesLayout(propertiesLayout);
				Iterator<Component> iterator = propertiesLayout.iterator();
				if (selected != null) {
					selected.removeStyleName("prueba");
				}
				selected = (HorizontalLayout) ((HorizontalLayout) event.getSource()).getParent();
				selected.setStyleName("prueba");
				while (iterator.hasNext()) {
					Component tmp = iterator.next();
					if (tmp.getId().equalsIgnoreCase(tableId)) {
						tmp.setVisible(true);
					}
				}
			}
		});

		return fieldLayout;
	}

	/**
	 * Hide all the layouts in the properties layout
	 * 
	 * @param propertiesLayout The layout with the properties
	 */
	private void hideAllPropertiesLayout(VerticalLayout propertiesLayout) {
		Iterator<Component> iterator = propertiesLayout.iterator();
		while (iterator.hasNext()) {
			iterator.next().setVisible(false);
		}
	}

	private void createNewPropertiesTable(VerticalLayout propertiesLayout, String tableId,
			DraggableField draggableField) {
		hideAllPropertiesLayout(propertiesLayout);
		try {
			VerticalLayout propertyValue = new VerticalLayout();
			VerticalLayout propertyTable = new VerticalLayout();
			HorizontalLayout propertyRow = new HorizontalLayout();

			// Add class property, this is used in Magnolia 5.7 previous versions
			/*
			 * TextField fieldClass = new TextField("class");
			 * fieldClass.setValue(String.valueOf(draggableField.getDefinition().getClass())
			 * .replace("class ", "")); propertyValue.addComponent(fieldClass);
			 * propertyRow.addComponent(propertyValue);
			 * propertyTable.addComponent(propertyRow);
			 */

			// Add the rest of properties
			Class<? extends ConfiguredFieldDefinition> classDefinition = draggableField.getDefinition().getClass();
			Field[] commonFields = ConfiguredFieldDefinition.class.getDeclaredFields();
			Field[] fields = (Field[]) ArrayUtils.addAll(commonFields, classDefinition.getDeclaredFields());
			for (int i = 0; i < fields.length; ++i) {
				Field field = fields[i];
				if (isValidField(field)) {
					propertyValue.addComponent(getComponentByFieldType(field, draggableField.getMagnoliaType()));
					propertyRow.addComponent(propertyValue);
					propertyTable.addComponent(propertyRow);
				}
			}

			propertyTable.setId(tableId);
			propertiesLayout.addComponent(propertyTable);

		} catch (Exception e) {
		}
	}

	private String getTableId(Class<? extends ConfiguredFieldDefinition> definition) {
		return definition.getSimpleName() + "_" + Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * Generates a component from one property of the field dragged
	 * 
	 * @param currentField Property of the dragged field
	 * @return A component that can be text, check or list
	 */
	private Component getComponentByFieldType(Field currentField, String magnoliaType) {

		if (!isSpecialField(currentField)) {

			Class fieldType = currentField.getType();
			switch (fieldType.getSimpleName()) {
			case "Boolean":
			case "boolean":
				return new CheckBox(currentField.getName(), false);
			default:
				return new TextField(currentField.getName());
			}

		} else {
			// There are fields that we need to rename or set a specific value
			switch (currentField.getName()) {
			case "transformerClass":
				ComboBox<String> cmbTransformer = new ComboBox<String>("transformerClass");
				cmbTransformer.setReadOnly(false);
				cmbTransformer.setItems(DialogConstants.transformerClasses);
				return cmbTransformer;
			case "identifierToPathConverter":
				ComboBox<String> cmbIdentifier = new ComboBox<String>("identifierToPathConverter");
				cmbIdentifier.setReadOnly(false);
				cmbIdentifier.setItems(DialogConstants.identifierToPathConverters);
				return cmbIdentifier;
			case "contentPreviewDefinition":
				ComboBox<String> cmbPreviews = new ComboBox<String>("contentPreviewDefinition");
				cmbPreviews.setReadOnly(false);
				cmbPreviews.setItems(DialogConstants.contentPreviewDefinitions);
				return cmbPreviews;
			case "lists":
			case "fieldEditable":
				return new CheckBox(currentField.getName(), true);
			case "fieldType":
				TextField text = new TextField(currentField.getName());
				text.setValue(magnoliaType);
				return text;
			}
		}

		return new TextField(currentField.getName());
	}

	/**
	 * Adds a button that removes the component from the dialog layout if the user
	 * clicks on it. This also removes the table with its properties asociated.
	 * 
	 * @param containerLayout  The container where the component is
	 * @param component        The component to be removed
	 * @param propertiesLayout The layout with all table properties
	 * @param tableId          The table if for this specific field
	 * @return A layout with the button
	 */
	private HorizontalLayout addRemoveButton(VerticalLayout containerLayout, HorizontalLayout component,
			VerticalLayout propertiesLayout, String tableId) {
		HorizontalLayout removeLayout = new HorizontalLayout();
		Label label = new Label();
		label.setCaption("<i class=\"fas fa-trash-alt\"></i>");
		label.setCaptionAsHtml(true);
		removeLayout.addComponent(label);
		removeLayout.addLayoutClickListener(new LayoutClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void layoutClick(LayoutClickEvent event) {
				Iterator<Component> iterator = propertiesLayout.iterator();
				while (iterator.hasNext()) {
					Component tmp = iterator.next();
					if (tmp.getId().equalsIgnoreCase(tableId)) {
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
		Label label = new Label();
		label.setCaption("<i class=\"fas fa-arrow-up\"></i>");
		label.setCaptionAsHtml(true);
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
		Label label = new Label();
		label.setCaption("<i class=\"fas fa-arrow-down\"></i>");
		label.setCaptionAsHtml(true);
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

	private boolean isValidField(Field field) {
		return !DialogConstants.noIncludeFields.contains(field.getName());
	}

	private boolean isSpecialField(Field field) {
		return DialogConstants.specialFields.contains(field.getName());
	}

	private List<Component> dialogActions(Map<String, ActionDefinition> actionDefinitionMap) {
		List<Component> actions = new ArrayList<>();
		actionDefinitionMap.forEach((name, actionDefinition) -> {
			String caption = Optional.ofNullable(actionDefinition.getLabel()).orElse(actionDefinition.getName());
			Button button = new Button(caption);
			button.addStyleName(actionDefinition.getName());
			button.addClickListener(e -> dialogWindow.close());
			actions.add(button);
		});

		return actions;
	}

}
