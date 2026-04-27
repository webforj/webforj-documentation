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
  public void testHeadingRendersCorrectText() {
    assertThat(viewerPage.getHeading()).isVisible();
    assertThat(viewerPage.getHeading()).hasText("Welcome to MarkdownViewer");
  }

  @Test
  public void testBoldTextRendersCorrectly() {
    assertThat(viewerPage.getBoldText()).isVisible();
    assertThat(viewerPage.getBoldText()).hasText("bold");
  }

  @Test
  public void testItalicTextRendersCorrectly() {
    assertThat(viewerPage.getItalicText()).isVisible();
    assertThat(viewerPage.getItalicText()).hasText("italic");
  }

  @Test
  public void testStrikethroughRendersCorrectly() {
    assertThat(viewerPage.getStrikethrough()).isVisible();
    assertThat(viewerPage.getStrikethrough()).hasText("strikethrough");
  }

  @Test
  public void testInlineCodeRendersCorrectly() {
    assertThat(viewerPage.getInlineCode()).isVisible();
    assertThat(viewerPage.getInlineCode()).hasText("inline code");
  }

  @Test
  public void testListRendersAllItems() {
    assertThat(viewerPage.getListItems()).hasCount(3);
    assertThat(viewerPage.getListItems().nth(0)).hasText("First item");
    assertThat(viewerPage.getListItems().nth(1)).hasText("Second item");
    assertThat(viewerPage.getListItems().nth(2)).hasText("Third item");
  }

  @Test
  public void testBlockquoteRendersCorrectText() {
    assertThat(viewerPage.getBlockquote()).isVisible();
    assertThat(viewerPage.getBlockquote()).containsText("Markdown makes formatting easy");
  }

  @Test
  public void testCodeBlockRendersJavaCode() {
    assertThat(viewerPage.getCodeBlock()).isVisible();
    assertThat(viewerPage.getCodeBlock()).containsText("MarkdownViewer viewer = new MarkdownViewer()");
  }
}