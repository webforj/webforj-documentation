package com.webforj.samples.pages.markdownviewer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MarkdownViewerPage {

  private static final String ROUTE = "markdownviewer";

  private final Locator markdownViewer;
  private final Locator heading;
  private final Locator boldText;
  private final Locator italicText;
  private final Locator codeText;
  private final Locator listItems;

  public MarkdownViewerPage(Page page) {
    this.markdownViewer = page.locator("dwc-markdown-viewer");
    this.heading = markdownViewer.locator("h1");
    this.boldText = markdownViewer.locator("strong");
    this.italicText = markdownViewer.locator("em");
    this.codeText = markdownViewer.locator("code");
    this.listItems = markdownViewer.locator("li");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getMarkdownViewer() {
    return markdownViewer;
  }

  public Locator getHeading() {
    return heading;
  }

  public Locator getBoldText() {
    return boldText;
  }

  public Locator getItalicText() {
    return italicText;
  }

  public Locator getCodeText() {
    return codeText;
  }

  public Locator getListItems() {
    return listItems;
  }
}