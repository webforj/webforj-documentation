package tests.ProgressBarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ProgressBarPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ProgressBarDeterminateIT extends BaseTest {

    private ProgressBarPage progressBar;

    @BeforeEach
    public void setupProgressBarDeterminate() {
        page.navigate("https://docs.webforj.com/webforj/progressbardeterminate?");
        progressBar = new ProgressBarPage(page);
    }

    @BrowserTest
    public void testLoadingTextStripedAnimationAndIndeterminateModeSettings() {

        assertThat(progressBar.getDeterminateProgressBar()).isVisible();
        assertThat(progressBar.getDeterminateProgressBar()).hasAttribute("animated", "true");

    }
} 