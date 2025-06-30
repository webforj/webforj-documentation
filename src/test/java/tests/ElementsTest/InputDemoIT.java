package tests.ElementsTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ElementsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class InputDemoIT extends BaseTest {

    private ElementsPage elements;

    @BeforeEach
    public void setupInputDemo() {
        page.navigate("https://docs.webforj.com/webforj/elementinputdemo?");
        elements = new ElementsPage(page);
    }

    @BrowserTest
    public void testInput() {
        assertThat(elements.getInput()).hasAttribute("placeholder", "Enter some text");

        elements.getInput().fill("Hello World");
        assertThat(elements.getInput()).hasValue("Hello World");
    }
} 