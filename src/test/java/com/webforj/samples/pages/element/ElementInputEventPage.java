package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class ElementInputEventPage extends BasePage {

    private static final String ROUTE = "elementinputevent";

    private final Locator inputField;
    private final Locator OKButton;
    private final Locator header;
    private final Locator section;

    public ElementInputEventPage(Page page) {
        super(page);

        inputField = page.locator(".element--input");
        Locator shadowRootDialogBox = page.locator("dwc-dialog[type='msgbox']");
        header = shadowRootDialogBox.locator("header");
        section = shadowRootDialogBox.locator("section");
        OKButton = page.locator("text=OK");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInputField() {
        return inputField;
    }

    public Locator getHeader() {
        return header;
    }

    public Locator getSection() {
        return section;
    }

    public Locator getOKButton() {
        return OKButton;
    }
}