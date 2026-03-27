package com.webforj.samples.pages.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class DialogClosePage {

    private static final String ROUTE = "dialogclose";

    private final Locator dialog;
    private final Locator closeDialogButton;
    private final Locator showDialogButton;

    public DialogClosePage(Page page) {
        this.dialog = page.getByRole(AriaRole.DIALOG);
        this.closeDialogButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Close Dialog"));
        this.showDialogButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Show Dialog"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDialog() {
        return dialog;
    }

    public Locator getCloseDialogButton() {
        return closeDialogButton;
    }

    public Locator getShowDialogButton() {
        return showDialogButton;
    }
}
