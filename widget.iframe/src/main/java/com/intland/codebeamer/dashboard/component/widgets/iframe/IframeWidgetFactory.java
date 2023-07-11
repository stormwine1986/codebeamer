package com.intland.codebeamer.dashboard.component.widgets.iframe;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.InjectableValues;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.common.interfaces.WidgetFactory;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttributeMapper;

/**
 * @author <a href="mailto:mark.szabo@intland.com">Mark Szabo</a>
 *
 */
@Component
@Qualifier("iframeWidgetFactory")
public class IframeWidgetFactory implements WidgetFactory<IframeWidget> {

	private final Renderer<IframeWidget> htmlRenderer;
	private final Renderer<IframeWidget> editorRenderer;
	private final WidgetAttributeMapper widgetAttributeMapper;

	@Autowired
	public IframeWidgetFactory(@Qualifier("iframeWidgetHtmlRenderer") final Renderer<IframeWidget> htmlRenderer,
			@Qualifier("iframeWidgetEditorRenderer") final Renderer<IframeWidget> editorRenderer,
			final WidgetAttributeMapper widgetAttributeMapper) {
		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
		this.widgetAttributeMapper = widgetAttributeMapper;
	}

	public InjectableValues getInjectableValues() {
    	final InjectableValues inject = new InjectableValues.Std()
		.addValue("iframeWidgetHtmlRenderer", htmlRenderer)
		.addValue("iframeWidgetEditorRenderer",editorRenderer);
    	return inject;
	}

	public Class<IframeWidget> getType() {
		return IframeWidget.class;
	}

	public String getTypeName() {
		return IframeWidget.class.getCanonicalName();
	}

	public IframeWidget createInstance() {
		return new IframeWidget(UUID.randomUUID().toString(), IframeWidget.getDescriptor(), htmlRenderer, editorRenderer);
	}

	public IframeWidget createInstance(final String id, final Map<String, String> attributes) {
		return createInstance(id, attributes, true);
	}

	public IframeWidget createInstance(final String id, final Map<String, String> attributes, final boolean validate) {
		final Map<String, WidgetAttribute> widgetAttributes = widgetAttributeMapper.map(attributes, IframeWidget.getDescriptor(), validate);

		return new IframeWidget(id, widgetAttributes, htmlRenderer, editorRenderer);
	}

}
