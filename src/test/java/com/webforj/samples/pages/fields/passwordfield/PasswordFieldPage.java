package com.webforj.samples.pages.fields.passwordfield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class PasswordFieldPage extends BasePage {

    private static final String ROUTE = "passwordfield";

    private final Locator passwordField;
    private final Locator eyeOffIcon;

    public PasswordFieldPage(Page page) {
        super(page);

        Locator shadowRootPasswordField = page.locator("dwc-field[type='password']");
        passwordField = shadowRootPasswordField.locator("input#field-1");
        eyeOffIcon = page.locator("dwc-icon-button[part='eye-off-icon']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getPasswordField() {
        return passwordField;
    }

    public Locator getEyeOffIcon() {
        return eyeOffIcon;
    }
}