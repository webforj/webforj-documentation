package com.webforj.samples.pages.markdownviewer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MarkdownViewerProgressivePage {

  private static final String ROUTE = "markdownviewerprogressive";

  private final Locator viewer;
  private final Locator startButton;
  private final Locator speedChoice;

  public MarkdownViewerProgressivePage(Page page) {
    this.viewer = page.locator("dwc-markdown-viewer");
    this.startButton = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
        new Page.GetByRoleOptions().setName("Start"));
    this.speedChoice = page.locator("dwc-choicebox");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getViewer() {
    return viewer;
  }

  public Locator getStartButton() {
    return startButton;
  }

  public Locator getSpeedChoice() {
    return speedChoice;
  }
}