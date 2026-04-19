package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.accordion.AccordionNestedPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AccordionNestedViewIT extends BaseTest {

    private AccordionNestedPage accordionNestedPage;

    public void setupAccordionNested(SupportedLanguage language) {
        navigateToRoute(AccordionNestedPage.getRoute(language));
        accordionNestedPage = new AccordionNestedPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testOuterPanelStartsOpened(SupportedLanguage language) {
        setupAccordionNested(language);
        assertThat(accordionNestedPage.getOuterPanel()).hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSiblingPanelStartsClosed(SupportedLanguage language) {
        setupAccordionNested(language);
        assertThat(accordionNestedPage.getSiblingPanel()).not().hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testInnerPanelsAreVisible(SupportedLanguage language) {
        setupAccordionNested(language);
        assertThat(accordionNestedPage.getInnerPanelA()).isVisible();
        assertThat(accordionNestedPage.getInnerPanelB()).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testInnerPanelStartsClosed(SupportedLanguage language) {
        setupAccordionNested(language);
        assertThat(accordionNestedPage.getInnerPanelA()).not().hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testClickingInnerPanelOpensIt(SupportedLanguage language) {
        setupAccordionNested(language);
        accordionNestedPage.getInnerPanelA().click();
        assertThat(accordionNestedPage.getInnerPanelA()).hasAttribute("opened", "");
    }
}
