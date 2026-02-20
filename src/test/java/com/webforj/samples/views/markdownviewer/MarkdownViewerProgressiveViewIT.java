package com.webforj.samples.views.markdownviewer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.markdownviewer.MarkdownViewerProgressivePage;
import com.webforj.samples.views.BaseTest;

public class MarkdownViewerProgressiveViewIT extends BaseTest {

  private MarkdownViewerProgressivePage progressivePage;

  @BeforeEach
  public void setup() {
    navigateToRoute(MarkdownViewerProgressivePage.getRoute());
    progressivePage = new MarkdownViewerProgressivePage(page);
  }

  @Test
  public void testInitialState() {
    assertThat(progressivePage.getStartButton()).isEnabled();
    assertThat(progressivePage.getStopButton()).isDisabled();
  }

  @Test
  public void testSpeedChoiceHasOptions() {
    assertThat(progressivePage.getSpeedChoice()).containsText("Default (4)");
  }

  @Test
  public void testClickingStartBeginsRendering() {
    progressivePage.getStartButton().click();

    assertThat(progressivePage.getStartButton()).isDisabled();
    assertThat(progressivePage.getStopButton()).isEnabled();

    progressivePage.getViewer().locator("h1").waitFor();
    assertThat(progressivePage.getViewer().locator("h1")).containsText("Octopus");
  }

  @Test
  public void testClickingStopHaltsRendering() {
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

  @Test
  public void testRenderingCompletesAndButtonsReset() {
    progressivePage.getStartButton().click();

    progressivePage.getViewer().locator("blockquote").waitFor(
        new com.microsoft.playwright.Locator.WaitForOptions().setTimeout(15000));

    assertThat(progressivePage.getStartButton()).isEnabled(
        new com.microsoft.playwright.assertions.LocatorAssertions.IsEnabledOptions().setTimeout(5000));
    assertThat(progressivePage.getStopButton()).isDisabled();
    assertThat(progressivePage.getViewer()).containsText("Peter Godfrey-Smith");
  }
}