package tests.AlertTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.AlertPage.AlertViewPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class AlertViewIT extends BaseTest {

    private AlertViewPage alertViewPage;

    @BeforeEach
    public void setupFormConfirmationAlert() {
        navigateToRoute(AlertViewPage.getRoute());
        alertViewPage = new AlertViewPage(page);
    }

    @BrowserTest
    public void testFormConfirmationAlert() {

        assertThat(alertViewPage.getFormAlert()).isVisible();
        assertThat(alertViewPage.getFormAlertText()).hasText("The requested information is ready to be viewed.");

        alertViewPage.getFormAlertButton().click();
        assertThat(alertViewPage.getFormAlertButton()).hasAttribute("has-focus", "");
    }

}