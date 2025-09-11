package com.webforj.samples.pages.slider;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SliderPage {

    private static final String ROUTE = "slider";

    private final Locator volumeOnButton;
    private final Locator volumeOffButton;
    private final Locator lowerHandle;

    public SliderPage(Page page) {

        this.volumeOffButton = page.getByRole( AriaRole.BUTTON, new Page.GetByRoleOptions().setName("volume off"));
        this.volumeOnButton = page.getByRole( AriaRole.BUTTON, new Page.GetByRoleOptions().setName("volume 2"));
        this.lowerHandle = page.getByRole(AriaRole.SLIDER);
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getVolumeOffButton() {
        return volumeOffButton;
    }

    public Locator getVolumeOnButton() {
        return volumeOnButton;
    }

    public Locator getLowerHandle() {
        return lowerHandle;
    }
}