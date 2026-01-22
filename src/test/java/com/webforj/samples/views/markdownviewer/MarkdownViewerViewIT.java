package com.webforj.samples.views.markdownviewer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.markdownviewer.MarkdownViewerPage;
import com.webforj.samples.views.BaseTest;

public class MarkdownViewerViewIT extends BaseTest {

  private MarkdownViewerPage markdownViewerPage;

  @BeforeEach
  public void setup() {
    navigateToRoute(MarkdownViewerPage.getRoute());
    markdownViewerPage = new MarkdownViewerPage(page);
  }

  @Test
  void shouldRenderMarkdownViewer() {
    assertThat(markdownViewerPage.getMarkdownViewer()).isVisible();
  }

  @Test
  void shouldRenderHeading() {
    assertThat(markdownViewerPage.getHeading()).containsText("Welcome to MarkdownViewer");
  }

  @Test
  void shouldRenderBoldText() {
    assertThat(markdownViewerPage.getBoldText()).containsText("bold");
  }

  @Test
  void shouldRenderItalicText() {
    assertThat(markdownViewerPage.getItalicText()).containsText("italic");
  }

  @Test
  void shouldRenderCodeText() {
    assertThat(markdownViewerPage.getCodeText()).containsText("code");
  }

  @Test
  void shouldRenderListItems() {
    assertThat(markdownViewerPage.getListItems()).hasCount(2);
  }
}