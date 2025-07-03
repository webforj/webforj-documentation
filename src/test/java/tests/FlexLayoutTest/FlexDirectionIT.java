package tests.FlexLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.FlexLayoutPage.FlexDirectionPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FlexDirectionIT extends BaseTest {

    private FlexDirectionPage flexDirectionPage;

    @BeforeEach
    public void setupFlexDirection() {
        navigateToRoute(FlexDirectionPage.getRoute());
        flexDirectionPage = new FlexDirectionPage(page);
    }

    @BrowserTest
    public void testRowArrangesBoxesHorizontallyInOrder() {
        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Row").click();

        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: row;.*"));

    }

    @BrowserTest
    public void testRowReverseArrangesBoxesHorizontallyInReverseOrder() {
        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Row-reverse").click();

        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: row-reverse;.*"));

    }

    @BrowserTest
    public void testColumnArrangesBoxesVerticallyInOrder() {
        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Column").click();

        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: column;.*"));
    }

    @BrowserTest
    public void testColumnReverseArrangesBoxesVerticallyInReverseOrder() {
        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Column-reverse").click();

        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: column-reverse;.*"));
    }
}