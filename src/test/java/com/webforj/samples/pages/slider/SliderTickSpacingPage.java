package com.webforj.samples.pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SliderTickSpacingPage {

    private static final String ROUTE = "slidertickspacing";

    private final Locator majorTickInput;
    private final Locator minorTickInput;
    private final Locator slider; 
    private final Locator snapToTicks;
    private final Locator showTicks;


    public SliderTickSpacingPage(Page page) {

        this.slider = page.getByRole(AriaRole.SLIDER);
        this.majorTickInput = page.getByLabel("Major Tick");
        this.minorTickInput = page.getByLabel("Minor Tick");
        this.snapToTicks = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Snap to Ticks"));
        this.showTicks = page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Show Ticks"));

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

    public Locator getSlider() {
        return slider;
    }

    public Locator getSnapToTicks() {
        return snapToTicks;
    }

    public Locator getShowTicks() {
        return showTicks;
    }

}