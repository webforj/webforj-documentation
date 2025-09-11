package com.webforj.samples.pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SliderLabelsPage {

    private static final String ROUTE = "sliderlabels";

    private final Locator tenDegreeOption;
    private final Locator fortyDegreeOption;
    private final Locator sliderLabel;

    public SliderLabelsPage(Page page) {

        this.sliderLabel = page.locator("dwc-slider");
        this.tenDegreeOption = page.getByText("10");
        this.fortyDegreeOption = page.getByText("40");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getTenDegreeOption() {
        return tenDegreeOption;
    }

    public Locator getFortyDegreeOption() {
        return fortyDegreeOption;
    }

    public Locator getSliderLabel() {
        return sliderLabel;
    }
}