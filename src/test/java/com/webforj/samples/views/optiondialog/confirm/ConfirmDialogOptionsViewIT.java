package com.webforj.samples.views.optiondialog.confirm;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.views.BaseTest;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ConfirmDialogOptionsViewIT extends BaseTest {

    private static final String ROUTE = "confirmdialogoptions";

    public void setupConfirmDialogOptionsView(SupportedLanguage language) {
        navigateToRoute(language.getPath(ROUTE));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDialogConfirmationIsShownWhenDiscardButtonIsClicked(SupportedLanguage language) {
        setupConfirmDialogOptionsView(language);
        Locator dialog = page.getByRole(AriaRole.DIALOG);
        assertThat(dialog).isVisible();

        Locator confirmButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Discard"));
        confirmButton.click();
        Locator discardedDialog = page.getByText("Changes discarded");
        assertThat(discardedDialog).isVisible();

        Locator gotItButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Got it"));
        gotItButton.click();

        assertThat(dialog).isVisible();

        Locator saveButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"));
        saveButton.click();

        Locator savedDialog = page.getByText("Changes saved");
        assertThat(savedDialog).isVisible();
        gotItButton.click();

        assertThat(dialog).isVisible();
    }
}