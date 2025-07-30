package tests.flexlayout.container;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.flexlayout.container.FlexDirectionPage;
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
    public void testFlexDirectionOptions() {
        // Test "Row"
        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Row").click();
        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: row;.*"));

        // Test "Row-reverse"
        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Row-reverse").click();
        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: row-reverse;.*"));

        // Test "Column"
        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Column").click();
        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: column;.*"));

        // Test "Column-reverse"
        flexDirectionPage.getFlexDirectionDropdown().click();
        flexDirectionPage.getListBox("Column-reverse").click();
        assertThat(flexDirectionPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: column-reverse;.*"));
    }
}