package com.magnolia.rd.dialogs.designer.utils;

import java.util.Optional;

import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.themes.ValoTheme;

public class TempClass {
	
	public TempClass() {
		Label draggableLabel = new Label("You can grab and drag me");
		DragSourceExtension<Label> dragSourceA = new DragSourceExtension<>(draggableLabel);
		draggableLabel.addStyleName("algo");

		// set the allowed effect
		dragSourceA.setEffectAllowed(EffectAllowed.MOVE);
		// set the text to transfer
		dragSourceA.setDataTransferText("hello receiver");
		// set other data to transfer (in this case HTML)
		dragSourceA.setDataTransferData("text/html", "<label>hello receiver</label>");

		dragSourceA.addDragStartListener(event -> Notification.show("Drag event started"));
		dragSourceA.addDragEndListener(event -> {
			if (event.isCanceled()) {
				Notification.show("Drag event was canceled");
			} else {
				Notification.show("Drag event finished");
			}
		});
		
		VerticalLayout dropTargetLayout = new VerticalLayout();
		dropTargetLayout.setCaption("Drop things inside me");
		dropTargetLayout.addStyleName(ValoTheme.LAYOUT_CARD);

		// make the layout accept drops
		DropTargetExtension<VerticalLayout> dropTarget = new DropTargetExtension<>(dropTargetLayout);

		// the drop effect must match the allowed effect in the drag source for a successful drop
		dropTarget.setDropEffect(DropEffect.MOVE);

		// catch the drops
		dropTarget.addDropListener(event -> {
		    // if the drag source is in the same UI as the target
		    Optional<AbstractComponent> dragSource = event.getDragSourceComponent();
		    if (dragSource.isPresent() && dragSource.get() instanceof Label) {
		        // move the label to the layout
		    	Label tmp = (Label) dragSource.get();
		        dropTargetLayout.addComponent(new Label(tmp.getValue()));

		        // get possible transfer data
		        Optional<String> message = event.getDataTransferData("text/html");
		        if (message != null) {
		            Notification.show("DropEvent with data transfer html: " + message);
		        } else {
		            // get transfer text
		            String messagee = event.getDataTransferText();
		            Notification.show("DropEvent with data transfer text: " + messagee);
		        }

		        // handle possible server side drag data, if the drag source was in the same UI
		        //event.getDragData().ifPresent(data -> handleMyDragData((MyObject) data));
		    }
		});
	}

}
