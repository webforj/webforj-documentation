package com.webforj.samples.views.markdownviewer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
  public void testViewerIsVisible() {
    assertThat(progressivePage.getViewer()).isVisible();
  }

  @Test
  public void testStartButtonIsVisible() {
    assertThat(progressivePage.getStartButton()).isVisible();
  }

  @Test
  public void testSpeedChoiceIsVisible() {
    assertThat(progressivePage.getSpeedChoice()).isVisible();
  }
}