package tests.AlertTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.AlertPage.ClosableAlertsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ClosableAlertsIT extends BaseTest {

    private ClosableAlertsPage closableAlertsPage;

    @BeforeEach
    public void setupClosableAlerts() {
        navigateToRoute(ClosableAlertsPage.getRoute());
        closableAlertsPage = new ClosableAlertsPage(page);
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