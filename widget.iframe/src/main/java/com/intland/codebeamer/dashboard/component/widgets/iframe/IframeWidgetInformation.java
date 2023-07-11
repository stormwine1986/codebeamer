package com.intland.codebeamer.dashboard.component.widgets.iframe;

import org.springframework.stereotype.Component;

import com.intland.codebeamer.dashboard.component.common.interfaces.WidgetInformation;
import com.intland.codebeamer.dashboard.component.widgets.common.WidgetCategory;

@Component
public class IframeWidgetInformation implements WidgetInformation {

	public String getCategory() {
		return WidgetCategory.OTHER.getName();
	}

	public String getImagePreviewUrl() {
		return "";
	}

	public String getKnowledgeBaseUrl() {
		return "";
	}

	public String getVendor() {
		return "CBC";
	}

	public String getName() {
		return "External Site Resources Widget";
	}

	public String getShortDescription() {
		return "use iframe to load external site pages";
	}

	public IframeWidgetFactory getFactory() {
		return null;
	}

	public String getType() {
		return IframeWidget.class.getCanonicalName();
	}
}
