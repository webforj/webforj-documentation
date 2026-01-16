package com.webforj.samples.pages.dialog;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

public class DialogBackdropBlurPage {

    private static final String ROUTE = "dialogbackdropblur";

    private final Locator dwcDialog;
    private final Locator backgroundBlur;

    public DialogBackdropBlurPage(Page page) {
        this.dwcDialog = page.locator("dwc-dialog");
        this.backgroundBlur = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Toggle Background Blur"));
}

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDwcDialog() {
        return dwcDialog;
    }

    public Locator getBackgroundBlur() {
        return backgroundBlur;
    }
}
