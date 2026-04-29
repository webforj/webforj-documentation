package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.accordion.AccordionMultiplePage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccordionMultipleViewIT extends BaseTest {

  private AccordionMultiplePage accordionMultiplePage;

  @BeforeEach
  public void setupAccordionMultiple() {
    navigateToRoute(AccordionMultiplePage.getRoute());
    accordionMultiplePage = new AccordionMultiplePage(page);
  }

  @Test
  public void testPanelAStartsOpened() {
    assertThat(accordionMultiplePage.getPanelA()).hasAttribute("opened", "");
  }

  @Test
  public void testPanelBStartsOpened() {
    assertThat(accordionMultiplePage.getPanelB()).hasAttribute("opened", "");
  }

  @Test
  public void testPanelCStartsClosed() {
    assertThat(accordionMultiplePage.getPanelC()).not().hasAttribute("opened", "");
  }

  @Test
  public void testCloseAllCollapsesAllPanels() {
    accordionMultiplePage.getCloseAllButton().click();

    assertThat(accordionMultiplePage.getPanelA()).not().hasAttribute("opened", "");
    assertThat(accordionMultiplePage.getPanelB()).not().hasAttribute("opened", "");
    assertThat(accordionMultiplePage.getPanelC()).not().hasAttribute("opened", "");
  }

  @Test
  public void testOpenAllExpandsAllPanels() {
    accordionMultiplePage.getCloseAllButton().click();
    accordionMultiplePage.getOpenAllButton().click();

    assertThat(accordionMultiplePage.getPanelA()).hasAttribute("opened", "");
    assertThat(accordionMultiplePage.getPanelB()).hasAttribute("opened", "");
    assertThat(accordionMultiplePage.getPanelC()).hasAttribute("opened", "");
  }
}
