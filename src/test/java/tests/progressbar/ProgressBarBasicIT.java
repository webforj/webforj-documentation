package tests.progressbar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.assertions.LocatorAssertions.HasAttributeOptions;

import pages.progressbar.ProgressBarBasicPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ProgressBarBasicIT extends BaseTest {

    private ProgressBarBasicPage progressBar;

    @BeforeEach
    public void setupProgressBarBasics() {
        navigateToRoute(ProgressBarBasicPage.getRoute());
        progressBar = new ProgressBarBasicPage(page);
    }

    @BrowserTest
    public void testStartProgressesBarTo100AndStops() {
        progressBar.getStartButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");
        assertThat(progressBar.getProgressBar()).hasAttribute("style", "--_dwc-progressbar-percent: 100%;",
                new HasAttributeOptions().setTimeout(30000));

    }

    @BrowserTest
    public void testResetReturnsToInitialStateAfterPausing() {
        progressBar.getStartButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");

        progressBar.getPauseButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "false");

        progressBar.getResetButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");

    }
}