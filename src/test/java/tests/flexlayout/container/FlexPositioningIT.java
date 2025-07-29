package tests.flexlayout.container;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.flexlayout.container.FlexPositioningPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FlexPositioningIT extends BaseTest {

    private FlexPositioningPage flexPositioningPage;

    @BeforeEach
    public void setupFlexPositioning() {
        navigateToRoute(FlexPositioningPage.getRoute());
        flexPositioningPage = new FlexPositioningPage(page);

    }

    @BrowserTest
    public void testFlexStartPositionsBoxesAtStart() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Flex-start").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: flex-start;.*"));
    }

    @BrowserTest
    public void testFlexEndPositionsBoxesAtEnd() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Flex-end").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: flex-end;.*"));
    }

    @BrowserTest
    public void testCenterPositionsBoxesCentrally() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Center").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: center;.*"));
    }

    @BrowserTest
    public void testSpaceBetweenDistributesBoxesWithEdges() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Space-between").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: space-between;.*"));
    }

    @BrowserTest
    public void testSpaceAroundDistributesBoxesWithEqualSpaceAround() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Space-around").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: space-around;.*"));
    }

    @BrowserTest
    public void testSpaceEvenlyDistributesBoxesWithEqualSpaceBetweenAndAround() {
        flexPositioningPage.getFlexPositioningDropdown().click();
        flexPositioningPage.getListBox("Space-evenly").nth(0).click();

        assertThat(flexPositioningPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: space-evenly;.*"));
    }
}