package tests.ProgressBarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ProgressBarPage.ProgressBarDeterminatePage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ProgressBarDeterminateIT extends BaseTest {

    private ProgressBarDeterminatePage progressBar;

    @BeforeEach
    public void setupProgressBarDeterminate() {
        navigateToRoute(ProgressBarDeterminatePage.getRoute());
        page.waitForLoadState();
        progressBar = new ProgressBarDeterminatePage(page);
    }

    @BrowserTest
    public void testLoadingTextStripedAnimationAndIndeterminateModeSettings() {

        assertThat(progressBar.getDeterminateProgressBar()).isVisible();
        assertThat(progressBar.getDeterminateProgressBar()).hasAttribute("animated", "true");

    }
}