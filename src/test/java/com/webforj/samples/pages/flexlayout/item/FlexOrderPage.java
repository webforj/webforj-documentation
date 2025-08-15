package com.webforj.samples.pages.flexlayout.item;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Locator.FilterOptions;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class FlexOrderPage extends BasePage {

    private static final String ROUTE = "flexorder";

    private final Locator flexOrderContainer;
    private final Locator orderInput;
    private final Locator setOrderButton;
    private final Locator dwcAlert;
    private final Locator hostInput;

    public FlexOrderPage(Page page) {
        super(page);

        hostInput = page.locator("dwc-numberfield")
                .filter(new FilterOptions().setHas(page.locator("label:has-text('Order')")));
        
        flexOrderContainer = page.locator(".button__container--single-row");
        orderInput = hostInput.locator("input[part^='input']:visible").first();
        Locator shadowRootOrderButton = page.locator("dwc-button[theme='default']");
        setOrderButton = shadowRootOrderButton.locator("button[type='button']");
        dwcAlert = hostInput.locator("dwc-alert-popover[theme='danger']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFlexOrderContainer() {
        return flexOrderContainer;
    }

    public Locator getOrderInput() {
        return orderInput;
    }

    public Locator getSetOrderButton() {
        return setOrderButton;
    }

    public Locator getDwcAlert() {
        return dwcAlert;
    }

    public Locator getHostInput() {
        return hostInput;
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");
    }
}