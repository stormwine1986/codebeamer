package com.intland.codebeamer.dashboard.component.widgets.iframe;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.StringAttribute;

@Component
@Qualifier("iframeWidgetHtmlRenderer")
public class IframeWidgetHtmlRenderer implements Renderer<IframeWidget> {

	private final String template = "<iframe src=\"{0}\" width=\"{1}\" height=\"{2}\" frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\"></iframe>";

	public String render(final RenderingContext renderingContext, final IframeWidget widget) {

		String name = renderingContext.getUser().getName();
		Integer id = renderingContext.getWikiContext().getProject().getId();
		String extra = "cb_user=" + name + "&cb_pid=" + id;

		String url = "";

		final StringAttribute urlAttribute = (StringAttribute) widget.getAttributes().get(IframeWidget.Attribute.URL.getKey());
		final StringAttribute widthAttribute = (StringAttribute) widget.getAttributes().get(IframeWidget.Attribute.Width.getKey());
		final StringAttribute heightAttribute = (StringAttribute) widget.getAttributes().get(IframeWidget.Attribute.Height.getKey());

		if (urlAttribute != null) {
			url = urlAttribute.getValue();
			if(url.indexOf("?")>-1){
				url += "&" + extra;
			}else{
				url += "?" + extra;
			}
		}
		
		return MessageFormat.format(template, url, widthAttribute.getValue().toString(), heightAttribute.getValue().toString());
	}

}
