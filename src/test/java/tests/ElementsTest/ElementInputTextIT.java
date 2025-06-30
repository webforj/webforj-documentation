package tests.ElementsTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import pages.ElementsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ElementInputTextIT extends BaseTest {

    private ElementsPage elements;

    @BeforeEach
    public void setupElementInputText() {
        page.navigate("https://docs.webforj.com/webforj/elementinputtext?");
        elements = new ElementsPage(page);
    }

    @BrowserTest
    public void testInputText() {
        Locator input = page.locator("input.element--input");

        assertThat(input).hasValue("Here is the set text");

        input.clear();
        assertThat(input).hasValue("");
    }
} 