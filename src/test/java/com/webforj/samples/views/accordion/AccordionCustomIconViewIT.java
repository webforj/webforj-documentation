package com.webforj.samples.views.accordion;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.accordion.AccordionCustomIconPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AccordionCustomIconViewIT extends BaseTest {

    private AccordionCustomIconPage accordionCustomIconPage;

    public void setupAccordionCustomIcon(SupportedLanguage language) {
        navigateToRoute(AccordionCustomIconPage.getRoute(language));
        accordionCustomIconPage = new AccordionCustomIconPage(page);
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testCustomIconPanelIsVisible(SupportedLanguage language) {
        setupAccordionCustomIcon(language);
        assertThat(accordionCustomIconPage.getCustomIconPanel()).isVisible();
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testCustomIconPanelHasIconSlot(SupportedLanguage language) {
        setupAccordionCustomIcon(language);
        assertThat(accordionCustomIconPage.getCustomIconPanel()
                .locator("[slot='icon']")).isAttached();
    }
}
