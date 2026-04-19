package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.accordion.AccordionGroupPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AccordionGroupViewIT extends BaseTest {

    private AccordionGroupPage accordionGroupPage;

    public void setupAccordionGroup(SupportedLanguage language) {
        navigateToRoute(AccordionGroupPage.getRoute(language));
        accordionGroupPage = new AccordionGroupPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testFirstPanelStartsOpened(SupportedLanguage language) {
        setupAccordionGroup(language);
        assertThat(accordionGroupPage.getPanelWebforJ()).hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSecondPanelStartsClosed(SupportedLanguage language) {
        setupAccordionGroup(language);
        assertThat(accordionGroupPage.getPanelGrouped()).not().hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testOpeningSecondPanelClosesFirst(SupportedLanguage language) {
        setupAccordionGroup(language);
        accordionGroupPage.getPanelGrouped().click();

        assertThat(accordionGroupPage.getPanelGrouped()).hasAttribute("opened", "");
        assertThat(accordionGroupPage.getPanelWebforJ()).not().hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testAllThreePanelsAreVisible(SupportedLanguage language) {
        setupAccordionGroup(language);
        assertThat(accordionGroupPage.getPanelWebforJ()).isVisible();
        assertThat(accordionGroupPage.getPanelGrouped()).isVisible();
        assertThat(accordionGroupPage.getPanelMultiple()).isVisible();
    }
}
