package tests.AlertTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.AlertPage.AlertExpansesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class AlertExpansesIT extends BaseTest {

    private AlertExpansesPage alertExpansesPage;

    @BeforeEach
    public void setupAlertExpanses() {
        navigateToRoute(AlertExpansesPage.getRoute());
        alertExpansesPage = new AlertExpansesPage(page);
    }

    @BrowserTest
    public void testAlertExpanses() {
        assertThat(alertExpansesPage.getNoneExpanseAlert()).hasAttribute("expanse", "");
        assertThat(alertExpansesPage.getXsmallExpanseAlert()).hasAttribute("expanse", "xs");
        assertThat(alertExpansesPage.getSmallExpanseAlert()).hasAttribute("expanse", "s");
        assertThat(alertExpansesPage.getMediumExpanseAlert()).hasAttribute("expanse", "m");
        assertThat(alertExpansesPage.getLargeExpanseAlert()).hasAttribute("expanse", "l");
        assertThat(alertExpansesPage.getXlargeExpanseAlert()).hasAttribute("expanse", "xl");
    }
}