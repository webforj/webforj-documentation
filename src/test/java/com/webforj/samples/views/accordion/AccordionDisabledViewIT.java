package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.accordion.AccordionDisabledPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccordionDisabledViewIT extends BaseTest {
  private AccordionDisabledPage accordionDisabledPage;

  @BeforeEach
  public void setupAccordionDisabled() {
    navigateToRoute(AccordionDisabledPage.getRoute());
    accordionDisabledPage = new AccordionDisabledPage(page);
  }

  @Test
  public void testDisabledPanelHasDisabledAttribute() {
    assertThat(accordionDisabledPage.getDisabledPanel()).hasAttribute("disabled", "");
  }

  @Test
  public void testDisabledOpenedPanelIsOpenedAndDisabled() {
    assertThat(accordionDisabledPage.getDisabledOpenedPanel()).hasAttribute("opened", "");
    assertThat(accordionDisabledPage.getDisabledOpenedPanel()).hasAttribute("disabled", "");
  }

  @Test
  public void testToggleButtonDisablesGroupPanels() {
    accordionDisabledPage.getToggleButton().click();
    assertThat(accordionDisabledPage.getGroupPanelOne()).hasAttribute("disabled", "");
  }

  @Test
  public void testToggleButtonReEnablesGroupPanels() {
    accordionDisabledPage.getToggleButton().click();
    accordionDisabledPage.getToggleButton().click();
    assertThat(accordionDisabledPage.getGroupPanelOne()).not().hasAttribute("disabled", "");
  }
}
