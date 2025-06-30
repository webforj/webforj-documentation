package tests.AlertTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.AlertPage.AlertThemesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class AlertThemesIT extends BaseTest {

    private AlertThemesPage alertThemesPage;

    @BeforeEach
    public void setupAlertThemes() {
        navigateToRoute(AlertThemesPage.getRoute());
        alertThemesPage = new AlertThemesPage(page);
    }

    @BrowserTest
    public void testAlertThemes() {
        assertThat(alertThemesPage.getDangerAlert()).isVisible();
        assertThat(alertThemesPage.getDefaultAlert()).isVisible();
        assertThat(alertThemesPage.getGrayAlert()).isVisible();
        assertThat(alertThemesPage.getInfoAlert()).isVisible();
        assertThat(alertThemesPage.getPrimaryAlert()).isVisible();
        assertThat(alertThemesPage.getSuccessAlert()).isVisible();
        assertThat(alertThemesPage.getWarningAlert()).isVisible();

        assertThat(alertThemesPage.getDangerAlert()).hasAttribute("theme", "danger");
        assertThat(alertThemesPage.getDefaultAlert()).hasAttribute("theme", "default");
        assertThat(alertThemesPage.getGrayAlert()).hasAttribute("theme", "gray");
        assertThat(alertThemesPage.getInfoAlert()).hasAttribute("theme", "info");
        assertThat(alertThemesPage.getPrimaryAlert()).hasAttribute("theme", "primary");
        assertThat(alertThemesPage.getSuccessAlert()).hasAttribute("theme", "success");
        assertThat(alertThemesPage.getWarningAlert()).hasAttribute("theme", "warning");

    }
} 