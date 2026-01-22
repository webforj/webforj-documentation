package com.webforj.samples.pages.markdownviewer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MarkdownViewerStreamingPage {

  private static final String ROUTE = "markdownviewerstreaming";

  private final Page page;
  private final Locator scrollContainer;
  private final Locator markdownViewer;
  private final Locator startButton;
  private final Locator stopButton;

  public MarkdownViewerStreamingPage(Page page) {
    this.page = page;
    this.scrollContainer = page.locator("div").filter(
        new Locator.FilterOptions().setHas(page.locator("dwc-markdown-viewer")));
    this.markdownViewer = page.locator("dwc-markdown-viewer");
    this.startButton = page.getByText("Start Stream");
    this.stopButton = page.getByText("Stop");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getScrollContainer() {
    return scrollContainer;
  }

  public Locator getMarkdownViewer() {
    return markdownViewer;
  }

  public Locator getStartButton() {
    return startButton;
  }

  public Locator getStopButton() {
    return stopButton;
  }

  public void clickStartStream() {
    startButton.click();
  }

  public void clickStop() {
    stopButton.click();
  }
}