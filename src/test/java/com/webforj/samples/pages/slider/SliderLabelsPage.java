package com.webforj.samples.pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class SliderLabelsPage extends BasePage {

    private static final String ROUTE = "sliderlabels";

    private final Locator tenDegreeOption;
    private final Locator fortyDegreeOption;
    private final Locator sliderLabel;

    public SliderLabelsPage(Page page) {
        super(page);

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