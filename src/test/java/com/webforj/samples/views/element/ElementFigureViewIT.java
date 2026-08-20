package com.webforj.samples.views.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.element.ElementFigurePage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElementFigureViewIT extends BaseTest {

  private ElementFigurePage elementFigurePage;

  @BeforeEach
  public void setupFigure() {
    navigateToRoute(ElementFigurePage.getRoute());
    elementFigurePage = new ElementFigurePage(page);
  }

  @Test
  public void testQuoteAndCaptionAreVisible() {
    assertThat(elementFigurePage.getQuote()).isVisible();
    assertThat(elementFigurePage.getCaption()).containsText("Dana Lee");
  }
}
