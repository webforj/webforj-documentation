package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.accordion.AccordionCustomHeaderPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccordionCustomHeaderViewIT extends BaseTest {

  private AccordionCustomHeaderPage accordionCustomHeaderPage;

  @BeforeEach
  public void setupAccordionCustomHeader() {
    navigateToRoute(AccordionCustomHeaderPage.getRoute());
    accordionCustomHeaderPage = new AccordionCustomHeaderPage(page);
  }

  @Test
  public void testCustomHeaderPanelIsVisible() {
    assertThat(accordionCustomHeaderPage.getCustomHeaderPanel()).isVisible();
  }

  @Test
  public void testUserSettingsPanelIsVisible() {
    assertThat(accordionCustomHeaderPage.getUserSettingsPanel()).isVisible();
  }

  @Test
  public void testClickingCustomHeaderPanelOpensIt() {
    accordionCustomHeaderPage.getCustomHeaderPanel().click();
    assertThat(accordionCustomHeaderPage.getCustomHeaderPanel()).hasAttribute("opened", "");
  }
}
