package tests.ProgressBarTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.assertions.LocatorAssertions.HasAttributeOptions;

import pages.ProgressBarPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ProgressBarBasicsIT extends BaseTest {

    private ProgressBarPage progressBar;

    @BeforeEach
    public void setupProgressBarBasics() {
        page.navigate("https://docs.webforj.com/webforj/progressbarbasic?");
        progressBar = new ProgressBarPage(page);
    }

    @BrowserTest
    public void testStartProgressesBarTo100AndStops() {

        assertThat(progressBar.getProgressBar()).hasAttribute("style", "--_dwc-progressbar-percent: 0%;");

        progressBar.getStartButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");
        assertThat(progressBar.getProgressBar()).hasAttribute("style", "--_dwc-progressbar-percent: 100%;",
                new HasAttributeOptions().setTimeout(15000));

    }

    @BrowserTest
    public void testPauseHaltsProgressAtCurrentValue() {

        assertThat(progressBar.getProgressBar()).hasAttribute("style", "--_dwc-progressbar-percent: 0%;");

        progressBar.getStartButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");

        progressBar.getPauseButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "false");
    }

    @BrowserTest
    public void testResetReturnsToInitialStateAfterPausing() {
        assertThat(progressBar.getProgressBar()).hasAttribute("style", "--_dwc-progressbar-percent: 0%;");

        progressBar.getStartButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");

        progressBar.getPauseButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "false");

        progressBar.getResetButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");

    }
} 