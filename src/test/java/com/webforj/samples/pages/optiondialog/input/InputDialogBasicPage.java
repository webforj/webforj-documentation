package com.webforj.samples.pages.optiondialog.input;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class InputDialogBasicPage {
    private static final String ROUTE = "inputdialogbasic";

    private final Locator dialog;
    private final Locator inputField;
    private final Locator deleteButton;
    private final Locator errorDialog;
    private final Locator okButton;
    private final Locator successDialog;

    public InputDialogBasicPage(Page page) {
        this.dialog = page.getByRole(AriaRole.DIALOG);
        this.inputField = page.getByRole(AriaRole.TEXTBOX);
        this.deleteButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete Repository"));
        this.errorDialog = page.getByText("Failed to delete the repository. Code entered is incorrect");
        this.okButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));
        this.successDialog = page.getByText("Repository was deleted successfully");
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

    public Locator getDeleteButton() {
        return deleteButton;
    }

    public Locator getErrorDialog() {
        return errorDialog;
    }

    public Locator getOKButton() {
        return okButton;
    }

    public Locator getSuccessDialog() {
        return successDialog;
    }
}
