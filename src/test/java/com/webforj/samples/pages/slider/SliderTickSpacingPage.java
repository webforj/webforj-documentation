package com.webforj.samples.pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class SliderTickSpacingPage extends BasePage {

    private static final String ROUTE = "slidertickspacing";

    private final Locator majorTickInput;
    private final Locator minorTickInput;
    private final Locator majorTicks;
    private final Locator minorTicks;
    private final Locator tickSpacingControl;
    private final Locator tickToggle;
    private final Locator snapToggle;
    private final Locator snapThumb;
    private final Locator lowerHandle;
    private final Locator shadowRootSlider;
    private final Locator majorFieldHost;
    private final Locator minorFieldHost;

    public SliderTickSpacingPage(Page page) {
        super(page);

        this.shadowRootSlider = page.locator("dwc-slider");
        this.majorFieldHost = page.locator("dwc-field")
                .filter(new Locator.FilterOptions().setHasText("Major Tick"));
        this.minorFieldHost = page.locator("dwc-field")
                .filter(new Locator.FilterOptions().setHasText("Minor Tick"));

        this.majorTickInput = majorFieldHost.locator("input[type='number']");
        this.minorTickInput = minorFieldHost.locator("input[type='number']");

        this.majorTicks = shadowRootSlider.locator(".noUi-value-horizontal");
        this.minorTicks = shadowRootSlider.locator(".noUi-marker-horizontal");
        this.tickSpacingControl = shadowRootSlider.locator("div[part='control']");
        this.snapToggle = page.locator("dwc-radio").filter(
                new Locator.FilterOptions().setHasText("Snap to Ticks"));
        this.tickToggle = page.locator("dwc-radio").filter(
                new Locator.FilterOptions().setHasText("Show Ticks"));
        this.snapThumb = shadowRootSlider.locator(".noUi-touch-area");
        this.lowerHandle = shadowRootSlider.locator(".noUi-handle-lower");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getMajorTickInput() {
        return majorTickInput;
    }

    public Locator getMinorTickInput() {
        return minorTickInput;
    }

    public Locator getMajorTicks() {
        return majorTicks;
    }

    public Locator getMinorTicks() {
        return minorTicks;
    }

    public Locator getTickSpacingControl() {
        return tickSpacingControl;
    }

    public Locator getTickToggle() {
        return tickToggle;
    }

    public Locator getSnapToggle() {
        return snapToggle;
    }

    public Locator getSnapThumb() {
        return snapThumb;
    }

    public Locator getLowerHandle() {
        return lowerHandle;
    }
}