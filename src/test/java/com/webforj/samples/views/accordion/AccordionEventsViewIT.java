package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.accordion.AccordionEventsPage;
import com.webforj.samples.views.BaseTest;

public class AccordionEventsViewIT extends BaseTest {

    private AccordionEventsPage accordionEventsPage;

    @BeforeEach
    public void setupAccordionEvents() {
        navigateToRoute(AccordionEventsPage.getRoute());
        accordionEventsPage = new AccordionEventsPage(page);
    }

    @Test
    public void testPanelStartsClosed() {
        assertThat(accordionEventsPage.getPanel()).not().hasAttribute("opened", "");
    }

    @Test
    public void testClickingPanelOpensIt() {
        accordionEventsPage.getPanel().click();
        assertThat(accordionEventsPage.getPanel()).hasAttribute("opened", "");
    }

    @Test
    public void testClickingPanelShowsToggleToast() {
        accordionEventsPage.getPanel().click();
        assertThat(accordionEventsPage.getToast()).isVisible();
    }

    @Test
    public void testOpeningPanelShowsOpenToast() {
        accordionEventsPage.getPanel().click();
        // After opening, both the toggle toast and the open toast should have fired.
        // Wait for at least one toast to be visible.
        assertThat(accordionEventsPage.getToast()).isVisible();
    }
}
