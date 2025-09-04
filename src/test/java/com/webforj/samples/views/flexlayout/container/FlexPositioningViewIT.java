package com.webforj.samples.views.flexlayout.container;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.flexlayout.container.FlexPositioningPage;
import com.webforj.samples.views.BaseTest;

public class FlexPositioningViewIT extends BaseTest {

    private FlexPositioningPage flexPositioningPage;

    @BeforeEach
    public void setupFlexPositioning() {
        navigateToRoute(FlexPositioningPage.getRoute());
        flexPositioningPage = new FlexPositioningPage(page);

    }

    @Test
    public void testFlexStartPositionsBoxesAtStart() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Flex-start").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*flex-start"));
    }

    @Test
    public void testFlexEndPositionsBoxesAtEnd() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Flex-end").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*flex-end"));
    }

    @Test
    public void testCenterPositionsBoxesCentrally() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Center").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*center"));
    }

    @Test
    public void testSpaceBetweenDistributesBoxesWithEdges() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Space-between").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*space-between"));
    }

    @Test
    public void testSpaceAroundDistributesBoxesWithEqualSpaceAround() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Space-around").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*space-around"));
    }

    @Test
    public void testSpaceEvenlyDistributesBoxesWithEqualSpaceBetweenAndAround() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Space-evenly").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile("justify-content:\\s*space-evenly"));
    }
}
