package tests.alert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.alert.ClosableAlertPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ClosableAlertIT extends BaseTest {

    private ClosableAlertPage closableAlertsPage;

    @BeforeEach
    public void setupClosableAlerts() {
        navigateToRoute(ClosableAlertPage.getRoute());
        closableAlertsPage = new ClosableAlertPage(page);
    }

    @BrowserTest
    public void testClosableAlerts() {
        assertThat(closableAlertsPage.getClosableAlert()).isVisible();

        assertThat(closableAlertsPage.getClosableAlertText()).hasText("Heads up! This alert can be dismissed.");

        closableAlertsPage.getClosableAlertButton().click();
        assertThat(closableAlertsPage.getClosableAlert()).not().hasAttribute("opened", "");

        closableAlertsPage.getShowAlertButton().click();
        assertThat(closableAlertsPage.getClosableAlert()).hasAttribute("opened", "");

    }
}