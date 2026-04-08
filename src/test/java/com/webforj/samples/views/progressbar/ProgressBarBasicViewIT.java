package com.webforj.samples.views.progressbar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.progressbar.ProgressBarBasicPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class ProgressBarBasicViewIT extends BaseTest {

  private ProgressBarBasicPage progressBar;

  public void setupProgressBarBasics(SupportedLanguage language) {
    navigateToRoute(ProgressBarBasicPage.getRoute(language));
    progressBar = new ProgressBarBasicPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testResetReturnsToInitialStateAfterPausing(SupportedLanguage language) {
    setupProgressBarBasics(language);
    progressBar.getStartButton().click();
    assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");

    progressBar.getPauseButton().click();
    assertThat(progressBar.getProgressBar()).hasAttribute("animated", "false");

    progressBar.getResetButton().click();
    assertThat(progressBar.getProgressBar()).hasAttribute("animated", "true");

  }
}
