package com.webforj.samples.views.optiondialog.input;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.views.BaseTest;

public class InputDialogTypeViewIT extends BaseTest {

    @BeforeEach
    public void setupInputDialogTypeView() {
        navigateToRoute("inputdialogtype");
    }

    @Test
    public void testInputDialogTypeView() {
        Locator dialog = page.getByRole(AriaRole.DIALOG);
        assertThat(dialog).isVisible();

        Locator inputField = page.getByRole(AriaRole.TEXTBOX);
        inputField.fill("mySecretPassword");
        Locator continueButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue"));
        continueButton.click();
        Locator accessGrantedDialog = page.getByText("Access granted");
        assertThat(accessGrantedDialog).isVisible();
        Locator gotItButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Got it"));
        gotItButton.click();
    }

}
