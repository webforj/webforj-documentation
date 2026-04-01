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
  public void testInitialState() {
    assertThat(streamingPage.getHeader()).containsText("AI Chat Demo");
    assertThat(streamingPage.getInputField()).isVisible();
    assertThat(streamingPage.getSendButton()).isVisible();
    assertThat(streamingPage.getStopButton()).not().isVisible();
  }

  @Test
  public void testSendingMessageShowsUserMessage() {
    streamingPage.sendMessage("Hello AI");
    assertThat(streamingPage.getViewer()).containsText("Hello AI");
  }

  @Test
  public void testSendingMessageShowsThinkingIndicator() {
    streamingPage.sendMessage("Test message");
    assertThat(streamingPage.getThinkingIndicator()).isVisible();
    assertThat(streamingPage.getThinkingIndicator()).containsText("Thinking...");
  }

  @Test
  public void testStopButtonAppearsWhileStreaming() {
    streamingPage.sendMessage("Generate response");

    streamingPage.getStopButton().waitFor(
        new com.microsoft.playwright.Locator.WaitForOptions().setTimeout(3000));

    assertThat(streamingPage.getStopButton()).isVisible();
    assertThat(streamingPage.getSendButton()).not().isVisible();
  }

  @Test
  public void testStopButtonStopsStreaming() {
    streamingPage.sendMessage("Generate response");

    streamingPage.getStopButton().waitFor(
        new com.microsoft.playwright.Locator.WaitForOptions().setTimeout(3000));

    streamingPage.getStopButton().click();

    assertThat(streamingPage.getSendButton()).isVisible();
    assertThat(streamingPage.getStopButton()).not().isVisible();
  }

  @Test
  public void testInputFieldClearsAfterSending() {
    streamingPage.getInputField().fill("Test message");
    streamingPage.getSendButton().click();
    assertThat(streamingPage.getInputField()).hasValue("");
  }

  @Test
  public void testEnterKeySendsMessage() {
    streamingPage.getInputField().fill("Enter key test");
    streamingPage.getInputField().press("Enter");
    assertThat(streamingPage.getViewer()).containsText("Enter key test");
  }

  @Test
  public void testEmptyMessageDoesNotSend() {
    streamingPage.getSendButton().click();
    assertThat(streamingPage.getThinkingIndicator()).not().isVisible();
  }

}