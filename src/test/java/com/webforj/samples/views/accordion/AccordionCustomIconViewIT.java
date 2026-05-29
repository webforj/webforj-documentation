package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.accordion.AccordionCustomIconPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccordionCustomIconViewIT extends BaseTest {

  private AccordionCustomIconPage accordionCustomIconPage;

  @BeforeEach
  public void setupAccordionCustomIcon() {
    navigateToRoute(AccordionCustomIconPage.getRoute());
    accordionCustomIconPage = new AccordionCustomIconPage(page);
  }

  @Test
  public void testCustomIconPanelIsVisible() {
    assertThat(accordionCustomIconPage.getCustomIconPanel()).isVisible();
  }

  @Test
  public void testCustomIconPanelHasIconSlot() {
    assertThat(accordionCustomIconPage.getCustomIconPanel().locator("[slot='icon']")).isAttached();
  }
}
