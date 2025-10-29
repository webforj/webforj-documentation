package com.webforj.samples.views.optiondialog.input;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.optiondialog.input.InputDialogTypePage;
import com.webforj.samples.views.BaseTest;

public class InputDialogTypeViewIT extends BaseTest {

    private InputDialogTypePage inputDialogTypePage;

    @BeforeEach
    public void setupInputDialogTypeView() {
        navigateToRoute(InputDialogTypePage.getRoute());
        inputDialogTypePage = new InputDialogTypePage(page);
    }

    @Test
        public void testInputDialogTypeIsShownWhenContinueButtonIsClicked() {

        inputDialogTypePage.getInputField().fill("mySecretPassword");
        inputDialogTypePage.getContinueButton().click();
        assertThat(inputDialogTypePage.getAccessGrantedDialog()).isVisible();
        inputDialogTypePage.getGotItButton().click();
    }

}
