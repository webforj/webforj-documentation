package com.webforj.samples.pages.element;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class ElementInputEventPage extends BasePage {

    private static final String ROUTE = "elementinputevent";

    private final Locator inputField;
    private final Locator OKButton;
    private final Locator dialogMessage;

    public ElementInputEventPage(Page page) {
        super(page);

        inputField = page.getByRole(AriaRole.TEXTBOX);

        dialogMessage = page.locator("section");
        OKButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getInputField() {
        return inputField;
    }

    public Locator getDialogMessage() {
        return dialogMessage;
    }

    public Locator getOKButton() {
        return OKButton;
    }
}