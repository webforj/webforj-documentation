package com.webforj.samples.views.markdownviewer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.markdownviewer.MarkdownViewerPage;
import com.webforj.samples.views.BaseTest;

public class MarkdownViewerViewIT extends BaseTest {

  private MarkdownViewerPage viewerPage;

  @BeforeEach
  public void setup() {
    navigateToRoute(MarkdownViewerPage.getRoute());
    viewerPage = new MarkdownViewerPage(page);
  }

  @Test
  public void testViewerIsVisible() {
    assertThat(viewerPage.getViewer()).isVisible();
  }

  @Test
  public void testHeadingIsRendered() {
    assertThat(viewerPage.getHeading()).isVisible();
    assertThat(viewerPage.getHeading()).containsText("Welcome to MarkdownViewer");
  }

  @Test
  public void testBoldTextIsRendered() {
    assertThat(viewerPage.getBoldText()).isVisible();
  }

  @Test
  public void testItalicTextIsRendered() {
    assertThat(viewerPage.getItalicText()).isVisible();
  }

  @Test
  public void testInlineCodeIsRendered() {
    assertThat(viewerPage.getInlineCode()).isVisible();
  }

  @Test
  public void testListItemsAreRendered() {
    assertThat(viewerPage.getListItems().first()).isVisible();
  }

  @Test
  public void testBlockquoteIsRendered() {
    assertThat(viewerPage.getBlockquote()).isVisible();
  }

  @Test
  public void testCodeBlockIsRendered() {
    assertThat(viewerPage.getCodeBlock()).isVisible();
  }
}