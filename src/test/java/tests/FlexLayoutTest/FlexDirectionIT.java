package tests.FlexLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.FlexLayoutPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class FlexDirectionIT extends BaseTest {

    private FlexLayoutPage flexPage;

    @BeforeEach
    public void setupFlexDirection() {
        page.navigate("https://docs.webforj.com/webforj/flexdirection?");
        flexPage = new FlexLayoutPage(page);
    }

    @BrowserTest
    public void testRowArrangesBoxesHorizontallyInOrder() {
        flexPage.getFlexDirectionDropdown().click();
        flexPage.getListBox("Row").click();

        assertThat(flexPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: row;.*"));

    }

    @BrowserTest
    public void testRowReverseArrangesBoxesHorizontallyInReverseOrder() {
        flexPage.getFlexDirectionDropdown().click();
        flexPage.getListBox("Row-reverse").click();

        assertThat(flexPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: row-reverse;.*"));

    }

    @BrowserTest
    public void testColumnArrangesBoxesVerticallyInOrder() {
        flexPage.getFlexDirectionDropdown().click();
        flexPage.getListBox("Column").click();

        assertThat(flexPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: column;.*"));
    }

    @BrowserTest
    public void testColumnReverseArrangesBoxesVerticallyInReverseOrder() {
        flexPage.getFlexDirectionDropdown().click();
        flexPage.getListBox("Column-reverse").click();

        assertThat(flexPage.getFlexDirectionContainer()).hasAttribute("style",
                Pattern.compile(".*flex-direction: column-reverse;.*"));
    }
} 