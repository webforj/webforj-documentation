package com.webforj.samples.pages.button;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.SupportedLanguage;

public class ButtonDisablePage {

    private static final String ROUTE = "buttondisable";

    private final Locator submitButton;
    private final Locator emailInput;

    public ButtonDisablePage(Page page) {
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        this.emailInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter an email"));
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getSubmitButton() {
        return submitButton;
    }

    public Locator getEmailInput() {
        return emailInput;
    }
}
