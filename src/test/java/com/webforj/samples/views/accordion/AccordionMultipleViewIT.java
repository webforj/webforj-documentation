package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.accordion.AccordionMultiplePage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AccordionMultipleViewIT extends BaseTest {

    private AccordionMultiplePage accordionMultiplePage;

    public void setupAccordionMultiple(SupportedLanguage language) {
        navigateToRoute(AccordionMultiplePage.getRoute(language));
        accordionMultiplePage = new AccordionMultiplePage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testPanelAStartsOpened(SupportedLanguage language) {
        setupAccordionMultiple(language);
        assertThat(accordionMultiplePage.getPanelA()).hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testPanelBStartsOpened(SupportedLanguage language) {
        setupAccordionMultiple(language);
        assertThat(accordionMultiplePage.getPanelB()).hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testPanelCStartsClosed(SupportedLanguage language) {
        setupAccordionMultiple(language);
        assertThat(accordionMultiplePage.getPanelC()).not().hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testCloseAllCollapsesAllPanels(SupportedLanguage language) {
        setupAccordionMultiple(language);
        accordionMultiplePage.getCloseAllButton().click();

        assertThat(accordionMultiplePage.getPanelA()).not().hasAttribute("opened", "");
        assertThat(accordionMultiplePage.getPanelB()).not().hasAttribute("opened", "");
        assertThat(accordionMultiplePage.getPanelC()).not().hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testOpenAllExpandsAllPanels(SupportedLanguage language) {
        setupAccordionMultiple(language);
        accordionMultiplePage.getCloseAllButton().click();
        accordionMultiplePage.getOpenAllButton().click();

        assertThat(accordionMultiplePage.getPanelA()).hasAttribute("opened", "");
        assertThat(accordionMultiplePage.getPanelB()).hasAttribute("opened", "");
        assertThat(accordionMultiplePage.getPanelC()).hasAttribute("opened", "");
    }
}
