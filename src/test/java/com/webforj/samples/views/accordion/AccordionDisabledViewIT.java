package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.accordion.AccordionDisabledPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AccordionDisabledViewIT extends BaseTest {
    private AccordionDisabledPage accordionDisabledPage;

    public void setupAccordionDisabled(SupportedLanguage language) {
        navigateToRoute(AccordionDisabledPage.getRoute(language));
        accordionDisabledPage = new AccordionDisabledPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDisabledPanelHasDisabledAttribute(SupportedLanguage language) {
        setupAccordionDisabled(language);
        assertThat(accordionDisabledPage.getDisabledPanel()).hasAttribute("disabled", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testDisabledOpenedPanelIsOpenedAndDisabled(SupportedLanguage language) {
        setupAccordionDisabled(language);
        assertThat(accordionDisabledPage.getDisabledOpenedPanel()).hasAttribute("opened", "");
        assertThat(accordionDisabledPage.getDisabledOpenedPanel()).hasAttribute("disabled", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testToggleButtonDisablesGroupPanels(SupportedLanguage language) {
        setupAccordionDisabled(language);
        accordionDisabledPage.getToggleButton().dispatchEvent("click");
        assertThat(accordionDisabledPage.getGroupPanelOne()).hasAttribute("disabled", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testToggleButtonReEnablesGroupPanels(SupportedLanguage language) {
        setupAccordionDisabled(language);
        accordionDisabledPage.getToggleButton().dispatchEvent("click");
        accordionDisabledPage.getToggleButton().dispatchEvent("click");
        assertThat(accordionDisabledPage.getGroupPanelOne()).not().hasAttribute("disabled", "");
    }
}