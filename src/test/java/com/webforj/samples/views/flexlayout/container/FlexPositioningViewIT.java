package com.webforj.samples.views.flexlayout.container;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import com.webforj.samples.pages.SupportedLanguage;
import com.webforj.samples.pages.flexlayout.container.FlexPositioningPage;
import com.webforj.samples.views.BaseTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class FlexPositioningViewIT extends BaseTest {

    private FlexPositioningPage flexPositioningPage;

    public void setupFlexPositioning(SupportedLanguage language) {
        navigateToRoute(FlexPositioningPage.getRoute(language));
        flexPositioningPage = new FlexPositioningPage(page);

    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testFlexStartPositionsBoxesAtStart(SupportedLanguage language) {
        setupFlexPositioning(language);
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Flex-start").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*flex-start"));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testFlexEndPositionsBoxesAtStart(SupportedLanguage language) {
        setupFlexPositioning(language);
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Flex-end").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*flex-end"));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testCenterPositionsBoxesAtStart(SupportedLanguage language) {
        setupFlexPositioning(language);
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Center").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*center"));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSpaceBetweenDistributesBoxesWithEdgesAtStart(SupportedLanguage language) {
        setupFlexPositioning(language);
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Space-between").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*space-between"));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSpaceAroundDistributesBoxesWithEqualSpaceAroundAtStart(SupportedLanguage language) {
        setupFlexPositioning(language);
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Space-around").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*space-around"));
    }

    @ParameterizedTest
    @MethodSource("provideRoutes")
    public void testSpaceEvenlyDistributesBoxesWithEqualSpaceBetweenAndAroundAtStart(SupportedLanguage language) {
        setupFlexPositioning(language);
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Space-evenly").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*space-evenly"));
    }
}
