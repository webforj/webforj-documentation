package com.webforj.samples.views.optiondialog.input;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.optiondialog.input.InputDialogBasicPage;
import com.webforj.samples.views.BaseTest;

public class InputDialogBasicViewIT extends BaseTest {

    private InputDialogBasicPage inputDialogBasicPage;

    @BeforeEach
    public void setupInputDialogBasicView() {
        navigateToRoute(InputDialogBasicPage.getRoute());
        inputDialogBasicPage = new InputDialogBasicPage(page);
    }

    @Test
    public void testInvalidInputDialogIsShownWhenDeleteButtonIsClicked() {
        inputDialogBasicPage.getInputField().fill("wrongCode");
        inputDialogBasicPage.getDeleteButton().click();
        assertThat(inputDialogBasicPage.getErrorDialog()).isVisible();
        inputDialogBasicPage.getOKButton().click();
    }

    @Test
    public void testValidInputDialogIsShownWhenDeleteButtonIsClicked() {
        inputDialogBasicPage.getInputField().fill("7ANfB");
        inputDialogBasicPage.getDeleteButton().click();
        assertThat(inputDialogBasicPage.getSuccessDialog()).isVisible();
        inputDialogBasicPage.getOKButton().click();
    }
}
