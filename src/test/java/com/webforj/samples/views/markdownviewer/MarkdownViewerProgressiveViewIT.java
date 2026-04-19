package com.webforj.samples.views.markdownviewer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.markdownviewer.MarkdownViewerProgressivePage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class MarkdownViewerProgressiveViewIT extends BaseTest {

  private MarkdownViewerProgressivePage progressivePage;

  public void setup(SupportedLanguage language) {
    navigateToRoute(MarkdownViewerProgressivePage.getRoute(language));
    progressivePage = new MarkdownViewerProgressivePage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testInitialState(SupportedLanguage language) {
    setup(language);
    assertThat(progressivePage.getStartButton()).isEnabled();
    assertThat(progressivePage.getStopButton()).isDisabled();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSpeedChoiceHasOptions(SupportedLanguage language) {
    setup(language);
    assertThat(progressivePage.getSpeedChoice()).containsText("Default (4)");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testClickingStartBeginsRendering(SupportedLanguage language) {
    setup(language);
    progressivePage.getStartButton().click();

    assertThat(progressivePage.getStartButton()).isDisabled();
    assertThat(progressivePage.getStopButton()).isEnabled();

    progressivePage.getViewer().locator("h1").waitFor();
    assertThat(progressivePage.getViewer().locator("h1")).containsText("Octopus");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testClickingStopHaltsRendering(SupportedLanguage language) {
    setup(language);
    progressivePage.getStartButton().click();
    progressivePage.getViewer().locator("h1").waitFor();
    progressivePage.getStopButton().click();

    assertThat(progressivePage.getStartButton()).isEnabled();
    assertThat(progressivePage.getStopButton()).isDisabled();

    String contentAfterStop = progressivePage.getViewer().textContent();
    page.waitForTimeout(500);
    String contentAfterWait = progressivePage.getViewer().textContent();

    assertTrue(contentAfterStop.length() == contentAfterWait.length(),
        "Content should not grow after stop is clicked");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testRenderingCompletesAndButtonsReset(SupportedLanguage language) {
    setup(language);
    progressivePage.getStartButton().click();

    progressivePage.getViewer().locator("blockquote").waitFor(
        new com.microsoft.playwright.Locator.WaitForOptions().setTimeout(15000));

    assertThat(progressivePage.getStartButton()).isEnabled(
        new com.microsoft.playwright.assertions.LocatorAssertions.IsEnabledOptions().setTimeout(5000));
    assertThat(progressivePage.getStopButton()).isDisabled();
    assertThat(progressivePage.getViewer()).containsText("Peter Godfrey-Smith");
  }
}