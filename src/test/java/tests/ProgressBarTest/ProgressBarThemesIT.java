package tests.ProgressBarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ProgressBarPage.ProgressBarThemesPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ProgressBarThemesIT extends BaseTest {

    private ProgressBarThemesPage progressBar;

    @BeforeEach
    public void setupProgressBarThemes() {
        navigateToRoute(ProgressBarThemesPage.getRoute());
        page.waitForLoadState();
        progressBar = new ProgressBarThemesPage(page);
    }

    @BrowserTest
    public void testEachThemeDisplaysCorrectColorAndHalfWidthWithProperLabel() {
        assertThat(progressBar.getDangerBar()).hasAttribute("theme", "danger");
        assertThat(progressBar.getDefaultBar()).hasAttribute("theme", "default");
        assertThat(progressBar.getGrayBar()).hasAttribute("theme", "gray");
        assertThat(progressBar.getInfoBar()).hasAttribute("theme", "info");
        assertThat(progressBar.getPrimaryBar()).hasAttribute("theme", "primary");
        assertThat(progressBar.getSuccessBar()).hasAttribute("theme", "success");
        assertThat(progressBar.getWarningBar()).hasAttribute("theme", "warning");
    }
}