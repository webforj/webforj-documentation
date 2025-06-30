package tests.ProgressBarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ProgressBarPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ProgressBarThemesIT extends BaseTest {

    private ProgressBarPage progressBar;

    @BeforeEach
    public void setupProgressBarThemes() {
        page.navigate("https://docs.webforj.com/webforj/progressbarthemes?");
        progressBar = new ProgressBarPage(page);
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