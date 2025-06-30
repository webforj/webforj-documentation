package tests.AlertTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.AlertPage.FormConfirmationAlertPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FormConfirmationAlertIT extends BaseTest {

    private FormConfirmationAlertPage formConfirmationAlertPage;

    @BeforeEach
    public void setupFormConfirmationAlert() {
        navigateToRoute(FormConfirmationAlertPage.getRoute());
        formConfirmationAlertPage = new FormConfirmationAlertPage(page);
    }

    @BrowserTest
    public void testFormConfirmationAlert() {

        assertThat(formConfirmationAlertPage.getFormAlert()).isVisible();
        assertThat(formConfirmationAlertPage.getFormAlertText()).hasText("The requested information is ready to be viewed.");

        formConfirmationAlertPage.getFormAlertButton().click();
        assertThat(formConfirmationAlertPage.getFormAlertButton()).hasAttribute("has-focus", "");
    }

} 