package com.webforj.samples.pages.markdownviewer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MarkdownViewerPage {

  private static final String ROUTE = "markdownviewer";

  private final Locator viewer;
  private final Locator heading;
  private final Locator boldText;
  private final Locator italicText;
  private final Locator strikethrough;
  private final Locator inlineCode;
  private final Locator listItems;
  private final Locator blockquote;
  private final Locator codeBlock;

  public MarkdownViewerPage(Page page) {
    this.viewer = page.locator("dwc-markdown-viewer");
    this.heading = viewer.locator("h1");
    this.boldText = viewer.locator("strong").first();
    this.italicText = viewer.locator("em").first();
    this.strikethrough = viewer.locator("s").first();
    this.inlineCode = viewer.locator("code").first();
    this.listItems = viewer.locator("li");
    this.blockquote = viewer.locator("blockquote");
    this.codeBlock = viewer.locator("pre code");
  }

  public static String getRoute() {
    return ROUTE;
  }

  public Locator getViewer() {
    return viewer;
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

  public Locator getStrikethrough() {
    return strikethrough;
  }

  public Locator getInlineCode() {
    return inlineCode;
  }

  public Locator getListItems() {
    return listItems;
  }

  public Locator getBlockquote() {
    return blockquote;
  }

  public Locator getCodeBlock() {
    return codeBlock;
  }
}