package com.webforj.samples.pages.optiondialog.input;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class InputDialogTypePage {
    private static final String ROUTE = "inputdialogtype";

    private final Locator dialog;
    private final Locator inputField;
    private final Locator continueButton;
    private final Locator accessGrantedDialog;
    private final Locator gotItButton;

    public InputDialogTypePage(Page page) {
        this.dialog = page.getByRole(AriaRole.DIALOG);
        this.inputField = page.getByRole(AriaRole.TEXTBOX);
        this.continueButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue"));
        this.accessGrantedDialog = page.getByText("Access granted");
        this.gotItButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Got it"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDialog() {
        return dialog;
    }

    public Locator getInputField() {
        return inputField;
    }

    public Locator getContinueButton() {
        return continueButton;
    }

    public Locator getAccessGrantedDialog() {
        return accessGrantedDialog;
    }

    public Locator getGotItButton() {
        return gotItButton;
    }
}
