package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.accordion.AccordionSlotsPage;
import com.webforj.samples.views.BaseTest;

public class AccordionSlotsViewIT extends BaseTest {

    private AccordionSlotsPage accordionSlotsPage;

    @BeforeEach
    public void setupAccordionSlots() {
        navigateToRoute(AccordionSlotsPage.getRoute());
        accordionSlotsPage = new AccordionSlotsPage(page);
    }

    @Test
    public void testCustomHeaderPanelIsVisible() {
        assertThat(accordionSlotsPage.getCustomHeaderPanel()).isVisible();
    }

    @Test
    public void testUserSettingsPanelIsVisible() {
        assertThat(accordionSlotsPage.getUserSettingsPanel()).isVisible();
    }

    @Test
    public void testCustomIconPanelIsVisible() {
        assertThat(accordionSlotsPage.getCustomIconPanel()).isVisible();
    }

    @Test
    public void testClickingCustomHeaderPanelOpensIt() {
        accordionSlotsPage.getCustomHeaderPanel().click();
        assertThat(accordionSlotsPage.getCustomHeaderPanel()).hasAttribute("opened", "");
    }

    @Test
    public void testCustomIconPanelHasIconSlot() {
        assertThat(accordionSlotsPage.getCustomIconPanel()
                .locator("[slot='icon']")).isAttached();
    }
}
