package com.webforj.samples.pages.flexlayout.item;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class FlexOrderPage extends BasePage {

    private static final String ROUTE = "flexorder";

    private final Locator flexOrderContainer;
    private final Locator orderInput;
    private final Locator setOrderButton;
    private final Locator dwcAlert;

    public FlexOrderPage(Page page) {
        super(page);

        flexOrderContainer = page.locator(".button__container--single-row");
        orderInput = page.locator("dwc-numberfield:has-text('Order') >> input[type='text']");
        setOrderButton = page.locator("dwc-button:has-text('Set Order')");
        dwcAlert = page.locator("dwc-alert-popover[theme='danger']");
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

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");
    }
}