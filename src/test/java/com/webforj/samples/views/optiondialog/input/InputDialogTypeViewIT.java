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

public class InputDialogTypeViewIT extends BaseTest {

    private static final String ROUTE = "inputdialogtype";

    public void setupInputDialogTypeView(SupportedLanguage language) {
        navigateToRoute(language.getPath(ROUTE));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testInputDialogTypeIsShownWhenContinueButtonIsClicked(SupportedLanguage language) {
        setupInputDialogTypeView(language);
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
