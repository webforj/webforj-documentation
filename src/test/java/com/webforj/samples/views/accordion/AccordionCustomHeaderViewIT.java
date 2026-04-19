package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.accordion.AccordionCustomHeaderPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AccordionCustomHeaderViewIT extends BaseTest {

    private AccordionCustomHeaderPage accordionCustomHeaderPage;

    public void setupAccordionCustomHeader(SupportedLanguage language) {
        navigateToRoute(AccordionCustomHeaderPage.getRoute(language));
        accordionCustomHeaderPage = new AccordionCustomHeaderPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testCustomHeaderPanelIsVisible(SupportedLanguage language) {
        setupAccordionCustomHeader(language);
        assertThat(accordionCustomHeaderPage.getCustomHeaderPanel()).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testUserSettingsPanelIsVisible(SupportedLanguage language) {
        setupAccordionCustomHeader(language);
        assertThat(accordionCustomHeaderPage.getUserSettingsPanel()).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testClickingCustomHeaderPanelOpensIt(SupportedLanguage language) {
        setupAccordionCustomHeader(language);
        accordionCustomHeaderPage.getCustomHeaderPanel().click();
        assertThat(accordionCustomHeaderPage.getCustomHeaderPanel()).hasAttribute("opened", "");
    }
}
