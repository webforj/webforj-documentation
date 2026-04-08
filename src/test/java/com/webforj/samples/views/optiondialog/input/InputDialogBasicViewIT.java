package com.webforj.samples.views.optiondialog.input;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.views.BaseTest;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class InputDialogBasicViewIT extends BaseTest {

    private static final String ROUTE = "inputdialogbasic";

    public void setupInputDialogBasicView(SupportedLanguage language) {
        navigateToRoute(language.getPath(ROUTE));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testInvalidInputDialogIsShownWhenDeleteButtonIsClicked(SupportedLanguage language) {
        setupInputDialogBasicView(language);
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

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testValidInputDialogIsShownWhenDeleteButtonIsClicked(SupportedLanguage language) {
        setupInputDialogBasicView(language);
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
