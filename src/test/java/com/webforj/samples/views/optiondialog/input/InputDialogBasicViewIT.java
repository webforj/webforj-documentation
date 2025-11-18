package com.webforj.samples.views.optiondialog.input;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.views.BaseTest;

public class InputDialogBasicViewIT extends BaseTest {

    @BeforeEach
    public void setupInputDialogBasicView() {
        navigateToRoute("inputdialogbasic");
    }

    @Test
    public void testInvalidInputDialogIsShownWhenDeleteButtonIsClicked() {
        Locator dialog = page.getByRole(AriaRole.DIALOG);
        assertThat(dialog).isVisible();

        Locator inputField = page.getByRole(AriaRole.TEXTBOX);
        inputField.fill("wrongCode");

        Locator deleteButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Delete Repository"));
        deleteButton.click();

        Locator errorDialog = page.getByText("Failed to delete the repository. Code entered is incorrect");
        assertThat(errorDialog).isVisible();

        Locator okButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));
        okButton.click();
    }

    @Test
    public void testValidInputDialogIsShownWhenDeleteButtonIsClicked() {
        Locator dialog = page.getByRole(AriaRole.DIALOG);
        assertThat(dialog).isVisible();

        Locator inputField = page.getByRole(AriaRole.TEXTBOX);
        Locator deleteButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Delete Repository"));

        inputField.fill("7ANfB");
        deleteButton.click();

        Locator okButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("OK"));
        Locator successDialog = page.getByText("Repository was deleted successfully");
        assertThat(successDialog).isVisible();
        okButton.click();
    }
}
