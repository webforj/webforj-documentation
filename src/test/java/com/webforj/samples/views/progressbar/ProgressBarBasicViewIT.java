package com.webforj.samples.views.progressbar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.progressbar.ProgressBarBasicPage;
import com.webforj.samples.views.BaseTest;

public class ProgressBarBasicViewIT extends BaseTest {

    private ProgressBarBasicPage progressBar;

    @BeforeEach
    public void setupProgressBarBasics() {
        navigateToRoute(ProgressBarBasicPage.getRoute());
        progressBar = new ProgressBarBasicPage(page);
    }

    @Test
    public void testResetReturnsToInitialStateAfterPausing() {
        progressBar.getStartButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");

        progressBar.getPauseButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "false");

        progressBar.getResetButton().click();
        assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");

    }
}
