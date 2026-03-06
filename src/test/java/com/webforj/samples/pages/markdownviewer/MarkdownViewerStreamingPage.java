package com.webforj.samples.pages.markdownviewer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MarkdownViewerStreamingPage {

  private static final String ROUTE = "markdownviewerstreaming";

  private final Locator viewer;
  private final Locator header;
  private final Locator inputField;
  private final Locator sendButton;
  private final Locator stopButton;
  private final Locator thinkingIndicator;

  public MarkdownViewerStreamingPage(Page page) {
    this.viewer = page.locator("dwc-markdown-viewer");
    this.header = page.locator(".chat__header");
    this.inputField = page.getByPlaceholder("Type a message...");
    this.sendButton = page.locator("dwc-button[theme='primary']");
    this.stopButton = page.locator("dwc-button[theme='danger']");
    this.thinkingIndicator = page.locator(".chat__thinking");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getViewer() {
    return viewer;
  }

  public Locator getHeader() {
    return header;
  }

  public Locator getInputField() {
    return inputField;
  }

  public Locator getSendButton() {
    return sendButton;
  }

  public Locator getStopButton() {
    return stopButton;
  }

  public Locator getThinkingIndicator() {
    return thinkingIndicator;
  }

  public void sendMessage(String message) {
    inputField.fill(message);
    sendButton.click();
  }
}