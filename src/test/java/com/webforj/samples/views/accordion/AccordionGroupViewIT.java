package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.accordion.AccordionGroupPage;
import com.webforj.samples.views.BaseTest;

public class AccordionGroupViewIT extends BaseTest {

    private AccordionGroupPage accordionGroupPage;

    @BeforeEach
    public void setupAccordionGroup() {
        navigateToRoute(AccordionGroupPage.getRoute());
        accordionGroupPage = new AccordionGroupPage(page);
    }

    @Test
    public void testFirstPanelStartsOpened() {
        assertThat(accordionGroupPage.getPanelWebforJ()).hasAttribute("opened", "");
    }

    @Test
    public void testSecondPanelStartsClosed() {
        assertThat(accordionGroupPage.getPanelGrouped()).not().hasAttribute("opened", "");
    }

    @Test
    public void testOpeningSecondPanelClosesFirst() {
        accordionGroupPage.getPanelGrouped().click();

        assertThat(accordionGroupPage.getPanelGrouped()).hasAttribute("opened", "");
        assertThat(accordionGroupPage.getPanelWebforJ()).not().hasAttribute("opened", "");
    }

    @Test
    public void testAllThreePanelsAreVisible() {
        assertThat(accordionGroupPage.getPanelWebforJ()).isVisible();
        assertThat(accordionGroupPage.getPanelGrouped()).isVisible();
        assertThat(accordionGroupPage.getPanelMultiple()).isVisible();
    }
}
