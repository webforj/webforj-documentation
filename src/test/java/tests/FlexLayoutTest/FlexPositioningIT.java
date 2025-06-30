package tests.FlexLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.FlexLayoutPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FlexPositioningIT extends BaseTest {

    private FlexLayoutPage flexPage;

    @BeforeEach
    public void setupFlexPositioning() {
        page.navigate("https://docs.webforj.com/webforj/flexpositioning?");
        flexPage = new FlexLayoutPage(page);

    }

    @BrowserTest
    public void testFlexStartPositionsBoxesAtStart() {
        flexPage.getFlexPositioningDropdown().click();
        flexPage.getListBox("Flex-start").nth(0).click();

        assertThat(flexPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: flex-start;.*"));
    }

    @BrowserTest
    public void testFlexEndPositionsBoxesAtEnd() {
        flexPage.getFlexPositioningDropdown().click();
        flexPage.getListBox("Flex-end").nth(0).click();

        assertThat(flexPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: flex-end;.*"));
    }

    @BrowserTest
    public void testCenterPositionsBoxesCentrally() {
        flexPage.getFlexPositioningDropdown().click();
        flexPage.getListBox("Center").nth(0).click();

        assertThat(flexPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: center;.*"));
    }

    @BrowserTest
    public void testSpaceBetweenDistributesBoxesWithEdges() {
        flexPage.getFlexPositioningDropdown().click();
        flexPage.getListBox("Space-between").nth(0).click();

        assertThat(flexPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: space-between;.*"));
    }

    @BrowserTest
    public void testSpaceAroundDistributesBoxesWithEqualSpaceAround() {
        flexPage.getFlexPositioningDropdown().click();
        flexPage.getListBox("Space-around").nth(0).click();

        assertThat(flexPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: space-around;.*"));
    }

    @BrowserTest
    public void testSpaceEvenlyDistributesBoxesWithEqualSpaceBetweenAndAround() {
        flexPage.getFlexPositioningDropdown().click();
        flexPage.getListBox("Space-evenly").nth(0).click();

        assertThat(flexPage.getFlexPositioningContainer()).hasAttribute("style",
                Pattern.compile(".*justify-content: space-evenly;.*"));
    }
} 