package com.intland.codebeamer.dashboard.component.widgets.iframe;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.AbstractWidget;
import com.intland.codebeamer.dashboard.component.widgets.common.WidgetAttributeWrapper;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.StringAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;

/**
 * @author <a href="mailto:mark.szabo@intland.com">Mark Szabo</a>
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic=true)
public class IframeWidget extends AbstractWidget {

	private static final String VERSION = "2.0.0.0";

	public static enum Attribute implements WidgetAttributeWrapper {
		URL("URL", new StringAttribute("https://www.ptc.com", true, false)),
		Width("Width", new StringAttribute("100%", true, false)),
		Height("Height", new StringAttribute("400", true, false));
		
		private String key;
		private WidgetAttribute defaultValue;

		private Attribute(String key, WidgetAttribute defaultValue) {
			this.key = key;
			this.defaultValue = defaultValue;
		}

		public String getKey() {
			return key;
		}

		public WidgetAttribute getDefaultValue() {
			return defaultValue;
		}
	}

	public static Map<String, WidgetAttribute> getDescriptor() {
		final Map<String, WidgetAttribute> result = new HashMap<String, WidgetAttribute>();

		result.put(Attribute.URL.getKey(), Attribute.URL.getDefaultValue());
		result.put(Attribute.Width.getKey(), Attribute.Width.getDefaultValue());
		result.put(Attribute.Height.getKey(), Attribute.Height.getDefaultValue());

		return result;

	}

	private final Renderer<IframeWidget> htmlRenderer;
	private final Renderer<IframeWidget> editorRenderer;

	/**
	 * @param id
	 * @param attributes
	 * @param htmlRenderer
	 * @param editorRenderer
	 */
	public IframeWidget(@JsonProperty("id") final String id, @JsonProperty("attributes") final Map<String, WidgetAttribute> attributes,
			@JacksonInject("iframeWidgetHtmlRenderer") final Renderer<IframeWidget> htmlRenderer,
			@JacksonInject("iframeWidgetEditorRenderer") final Renderer<IframeWidget> editorRenderer) {
		super(id, attributes);

		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
	}

	public String getTypeName() {
		return IframeWidget.class.getCanonicalName();
	}

	public String renderToHtml(final RenderingContext renderingContext) {
		return htmlRenderer.render(renderingContext, this);
	}

	public String renderEditorToHtml(final RenderingContext renderingContext) {
		return editorRenderer.render(renderingContext, this);
	}

	public String getDefaultTitleKey() {
		return "IFrame Widget";
	}

	public String getVersion() {
		return VERSION;
	}

	@Override
	public String toString() {
		return "IframeWidget [id=" + id + ", attributes=" + attributes + ", htmlRenderer=" + htmlRenderer
				+ ", editorRenderer=" + editorRenderer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 311;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((editorRenderer == null) ? 0 : editorRenderer.hashCode());
		result = prime * result + ((htmlRenderer == null) ? 0 : htmlRenderer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IframeWidget other = (IframeWidget) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (editorRenderer == null) {
			if (other.editorRenderer != null)
				return false;
		} else if (!editorRenderer.equals(other.editorRenderer))
			return false;
		if (htmlRenderer == null) {
			if (other.htmlRenderer != null)
				return false;
		} else if (!htmlRenderer.equals(other.htmlRenderer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
