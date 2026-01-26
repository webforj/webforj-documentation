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
  public void testHeaderIsVisible() {
    assertThat(streamingPage.getHeader()).isVisible();
    assertThat(streamingPage.getHeader()).containsText("AI Chat Demo");
  }

  @Test
  public void testInputFieldIsVisible() {
    assertThat(streamingPage.getInputField()).isVisible();
  }

  @Test
  public void testSendButtonIsVisible() {
    assertThat(streamingPage.getSendButton()).isVisible();
  }

  @Test
  public void testStopButtonIsHiddenInitially() {
    assertThat(streamingPage.getStopButton()).not().isVisible();
  }
}