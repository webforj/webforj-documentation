package com.webforj.samples.views.markdownviewer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.markdownviewer.MarkdownViewerStreamingPage;
import com.webforj.samples.views.BaseTest;

public class MarkdownViewerStreamingViewIT extends BaseTest {

  private MarkdownViewerStreamingPage streamingPage;

  @BeforeEach
  public void setup() {
    navigateToRoute(MarkdownViewerStreamingPage.getRoute());
    streamingPage = new MarkdownViewerStreamingPage(page);
  }

  @Test
  void shouldShowStartButtonInitially() {
    assertThat(streamingPage.getStartButton()).isVisible();
  }

  @Test
  void shouldHideStopButtonInitially() {
    assertThat(streamingPage.getStopButton()).not().isVisible();
  }

  @Test
  void shouldShowStopButtonWhenStreamStarts() {
    streamingPage.clickStartStream();
    assertThat(streamingPage.getStopButton()).isVisible();
  }

  @Test
  void shouldHideStartButtonWhenStreamStarts() {
    streamingPage.clickStartStream();
    assertThat(streamingPage.getStartButton()).not().isVisible();
  }

  @Test
  void shouldStopStreamWhenStopClicked() {
    streamingPage.clickStartStream();
    streamingPage.clickStop();
    assertThat(streamingPage.getStartButton()).isVisible();
    assertThat(streamingPage.getStopButton()).not().isVisible();
  }
}