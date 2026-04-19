package com.webforj.samples.views.markdownviewer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.markdownviewer.MarkdownViewerPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class MarkdownViewerViewIT extends BaseTest {

  private MarkdownViewerPage viewerPage;

  public void setup(SupportedLanguage language) {
    navigateToRoute(MarkdownViewerPage.getRoute(language));
    viewerPage = new MarkdownViewerPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testHeadingRendersCorrectText(SupportedLanguage language) {
    setup(language);
    assertThat(viewerPage.getHeading()).isVisible();
    assertThat(viewerPage.getHeading()).hasText("Welcome to MarkdownViewer");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testBoldTextRendersCorrectly(SupportedLanguage language) {
    setup(language);
    assertThat(viewerPage.getBoldText()).isVisible();
    assertThat(viewerPage.getBoldText()).hasText("bold");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testItalicTextRendersCorrectly(SupportedLanguage language) {
    setup(language);
    assertThat(viewerPage.getItalicText()).isVisible();
    assertThat(viewerPage.getItalicText()).hasText("italic");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testStrikethroughRendersCorrectly(SupportedLanguage language) {
    setup(language);
    assertThat(viewerPage.getStrikethrough()).isVisible();
    assertThat(viewerPage.getStrikethrough()).hasText("strikethrough");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testInlineCodeRendersCorrectly(SupportedLanguage language) {
    setup(language);
    assertThat(viewerPage.getInlineCode()).isVisible();
    assertThat(viewerPage.getInlineCode()).hasText("inline code");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testListRendersAllItems(SupportedLanguage language) {
    setup(language);
    assertThat(viewerPage.getListItems()).hasCount(3);
    assertThat(viewerPage.getListItems().nth(0)).hasText("First item");
    assertThat(viewerPage.getListItems().nth(1)).hasText("Second item");
    assertThat(viewerPage.getListItems().nth(2)).hasText("Third item");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testBlockquoteRendersCorrectText(SupportedLanguage language) {
    setup(language);
    assertThat(viewerPage.getBlockquote()).isVisible();
    assertThat(viewerPage.getBlockquote()).containsText("Markdown makes formatting easy");
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testCodeBlockRendersJavaCode(SupportedLanguage language) {
    setup(language);
    assertThat(viewerPage.getCodeBlock()).isVisible();
    assertThat(viewerPage.getCodeBlock()).containsText("MarkdownViewer viewer = new MarkdownViewer()");
  }
}