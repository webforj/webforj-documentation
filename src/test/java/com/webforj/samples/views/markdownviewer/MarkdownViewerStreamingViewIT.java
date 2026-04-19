package com.webforj.samples.views.markdownviewer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.markdownviewer.MarkdownViewerStreamingPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class MarkdownViewerStreamingViewIT extends BaseTest {

  private MarkdownViewerStreamingPage streamingPage;

  public void setup(SupportedLanguage language) {
    navigateToRoute(MarkdownViewerStreamingPage.getRoute(language));
    streamingPage = new MarkdownViewerStreamingPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testInitialState(SupportedLanguage language) {
    setup(language);
    assertThat(streamingPage.getHeader()).containsText("AI Chat Demo");
    assertThat(streamingPage.getInputField()).isVisible();
    assertThat(streamingPage.getSendButton()).isVisible();
    assertThat(streamingPage.getStopButton()).not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSendingMessageShowsUserMessage(SupportedLanguage language) {
    setup(language);
    streamingPage.sendMessage("Hello AI");
    assertThat(streamingPage.getViewer()).containsText("Hello AI");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSendingMessageShowsThinkingIndicator(SupportedLanguage language) {
    setup(language);
    streamingPage.sendMessage("Test message");
    assertThat(streamingPage.getThinkingIndicator()).isVisible();
    assertThat(streamingPage.getThinkingIndicator()).containsText("Thinking...");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testStopButtonAppearsWhileStreaming(SupportedLanguage language) {
    setup(language);
    streamingPage.sendMessage("Generate response");

    streamingPage.getStopButton().waitFor(
        new com.microsoft.playwright.Locator.WaitForOptions().setTimeout(3000));

    assertThat(streamingPage.getStopButton()).isVisible();
    assertThat(streamingPage.getSendButton()).not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testStopButtonStopsStreaming(SupportedLanguage language) {
    setup(language);
    streamingPage.sendMessage("Generate response");

    streamingPage.getStopButton().waitFor(
        new com.microsoft.playwright.Locator.WaitForOptions().setTimeout(3000));

    streamingPage.getStopButton().click();

    assertThat(streamingPage.getSendButton()).isVisible();
    assertThat(streamingPage.getStopButton()).not().isVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testInputFieldClearsAfterSending(SupportedLanguage language) {
    setup(language);
    streamingPage.getInputField().fill("Test message");
    streamingPage.getSendButton().click();
    assertThat(streamingPage.getInputField()).hasValue("");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testEnterKeySendsMessage(SupportedLanguage language) {
    setup(language);
    streamingPage.getInputField().fill("Enter key test");
    streamingPage.getInputField().press("Enter");
    assertThat(streamingPage.getViewer()).containsText("Enter key test");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testEmptyMessageDoesNotSend(SupportedLanguage language) {
    setup(language);
    streamingPage.getSendButton().click();
    assertThat(streamingPage.getThinkingIndicator()).not().isVisible();
  }

}