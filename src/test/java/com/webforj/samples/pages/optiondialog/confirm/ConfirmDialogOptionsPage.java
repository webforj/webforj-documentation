package com.webforj.samples.pages.optiondialog.confirm;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ConfirmDialogOptionsPage {
    private static final String ROUTE = "confirmdialogoptions";

    private final Locator dialog;
    private final Locator discardButton;
    private final Locator saveButton;
    private final Locator gotItButton;
    private final Locator discardedDialog;
    private final Locator savedDialog;

    public ConfirmDialogOptionsPage(Page page) {
        this.dialog = page.getByRole(AriaRole.DIALOG);
        this.discardButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Discard"));
        this.saveButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"));
        this.gotItButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Got it"));
        this.discardedDialog = page.getByText("Changes discarded");
        this.savedDialog = page.getByText("Changes saved");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDialog() {
        return dialog;
    }

    public Locator getDiscardButton() {
        return discardButton;
    }

    public Locator getSaveButton() {
        return saveButton;
    }

    public Locator getGotItButton() {
        return gotItButton;
    }

    public Locator getDiscardedDialog() {
        return discardedDialog;
    }

    public Locator getSavedDialog() {
        return savedDialog;
    }
}
