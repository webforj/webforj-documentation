package com.webforj.samples.pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class SliderPage extends BasePage {

    private static final String ROUTE = "slider";

    private final Locator muteIcon;
    private final Locator unmuteIcon;
    private final Locator lowerHandle;

    public SliderPage(Page page) {
        super(page);

       this.muteIcon = page.locator("dwc-icon-button[name='volume-off']");
       this.unmuteIcon = page.locator("dwc-icon-button[name='volume-2']");
       this.lowerHandle = page.locator("dwc-slider").locator(".noUi-handle-lower");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getMuteIcon() {
        return muteIcon;
    }

    public Locator getUnmuteIcon() {
        return unmuteIcon;
    }

    public Locator getLowerHandle() {
        return lowerHandle;
    }
}