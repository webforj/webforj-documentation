package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.accordion.AccordionBasicPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AccordionBasicViewIT extends BaseTest {

    private AccordionBasicPage accordionBasicPage;

    public void setupAccordionBasic(SupportedLanguage language) {
        navigateToRoute(AccordionBasicPage.getRoute(language));
        accordionBasicPage = new AccordionBasicPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSectionOneStartsOpened(SupportedLanguage language) {
        setupAccordionBasic(language);
        assertThat(accordionBasicPage.getSectionOne()).hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSectionTwoStartsClosed(SupportedLanguage language) {
        setupAccordionBasic(language);
        assertThat(accordionBasicPage.getSectionTwo()).not().hasAttribute("opened", "");
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSectionThreeIsVisible(SupportedLanguage language) {
        setupAccordionBasic(language);
        assertThat(accordionBasicPage.getSectionThree()).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testClickingSectionTwoOpensIt(SupportedLanguage language) {
        setupAccordionBasic(language);
        accordionBasicPage.getSectionTwo().click();
        assertThat(accordionBasicPage.getSectionTwo()).hasAttribute("opened", "");
    }
}
