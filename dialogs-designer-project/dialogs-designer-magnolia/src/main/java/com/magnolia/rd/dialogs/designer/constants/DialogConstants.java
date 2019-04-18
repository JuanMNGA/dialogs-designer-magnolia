package com.magnolia.rd.dialogs.designer.constants;

import java.util.Arrays;
import java.util.List;

public class DialogConstants {
	
	public static final String STYLE_BORDER = "layout-border";
	
	public static final String MODULE_PATH = "/modules/dialogs-designer-magnolia";
	public static final String DIALOG_FOLDER = "dialogs";
	
	public static final List<String> specialFields = Arrays.asList("converterClass", "lists");
	public static final List<String> noIncludeFields = Arrays.asList("validators");
	public static final List<String> transformerClasses = Arrays.asList(
			"info.magnolia.ui.form.field.transformer.basic.BasicTransformer",
			"info.magnolia.ui.form.field.transformer.composite.CompositeTransformer",
			"info.magnolia.ui.form.field.transformer.composite.SwitchableTransformer",
			"info.magnolia.ui.form.field.transformer.multi.MultiValueTransformer",
			"info.magnolia.ui.form.field.transformer.composite.DelegatingCompositeFieldTransformer",
			"info.magnolia.ui.form.field.transformer.multi.DelegatingMultiValueFieldTransformer",
			"info.magnolia.ui.form.field.transformer.composite.NoOpCompositeTransformer",
			"info.magnolia.ui.form.field.transformer.multi.MultiValueJSONTransformer",
			"info.magnolia.ui.form.field.transformer.multi.MultiValueChildrenNodeTransformer",
			"info.magnolia.ui.form.field.transformer.multi.MultiValueChildNodeTransformer",
			"info.magnolia.ui.form.field.transformer.multi.MultiValueSubChildrenNodeTransformer");

}
