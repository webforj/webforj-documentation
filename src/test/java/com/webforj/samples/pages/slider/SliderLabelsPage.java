package com.webforj.samples.pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class SliderLabelsPage extends BasePage {

    private static final String ROUTE = "sliderlabels";

    private final Locator tenDegreeOption;
    private final Locator fortyDegreeOption;
    private final Locator sixtyDegreeOption;
    private final Locator ninetyDegreeOption;
    private final Locator sliderLabel;
    private final Locator shadowRootSlider;

    public SliderLabelsPage(Page page) {
        super(page);

        this.shadowRootSlider = page.locator("dwc-slider");

        this.tenDegreeOption = shadowRootSlider.locator("div[data-value='10']");
        this.fortyDegreeOption = shadowRootSlider.locator("div[data-value='40']");
        this.sixtyDegreeOption = shadowRootSlider.locator("div[data-value='60']");
        this.ninetyDegreeOption = shadowRootSlider.locator("div[data-value='90']");
        this.sliderLabel = shadowRootSlider;
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

    public Locator getSixtyDegreeOption() {
        return sixtyDegreeOption;
    }

    public Locator getNinetyDegreeOption() {
        return ninetyDegreeOption;
    }

    public Locator getSliderLabel() {
        return sliderLabel;
    }
}