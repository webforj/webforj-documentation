package com.webforj.samples.views.optiondialog.confirm;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.views.BaseTest;
import com.webforj.samples.pages.optiondialog.confirm.ConfirmDialogOptionsPage;

public class ConfirmDialogOptionsViewIT extends BaseTest {

    private ConfirmDialogOptionsPage confirmDialogOptionsPage;

    @BeforeEach
    public void setupConfirmDialogOptionsView() {
        navigateToRoute(ConfirmDialogOptionsPage.getRoute());
        confirmDialogOptionsPage = new ConfirmDialogOptionsPage(page);
    }

    @Test
    public void testDialogConfirmationIsShownWhenDiscardButtonIsClicked() {
        confirmDialogOptionsPage.getDiscardButton().click();
        assertThat(confirmDialogOptionsPage.getDiscardedDialog()).isVisible();

        confirmDialogOptionsPage.getGotItButton().click();

        confirmDialogOptionsPage.getSaveButton().click();
        assertThat(confirmDialogOptionsPage.getSavedDialog()).isVisible();
    }
}