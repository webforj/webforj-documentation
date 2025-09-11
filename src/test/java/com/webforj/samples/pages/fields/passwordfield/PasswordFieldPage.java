package com.webforj.samples.pages.fields.passwordfield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class PasswordFieldPage {

    private static final String ROUTE = "passwordfield";

    private final Locator passwordField;
    private final Locator eyeOffIcon;

    public PasswordFieldPage(Page page) {
        
        passwordField = page.getByPlaceholder("Password");
        eyeOffIcon = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("eye"));
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