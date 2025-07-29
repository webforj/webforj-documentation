package tests.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.element.ElementInputDemoPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ElementInputDemoIT extends BaseTest {

    private ElementInputDemoPage elementInputDemoPage;

    @BeforeEach
    public void setupInputDemo() {
        navigateToRoute(ElementInputDemoPage.getRoute());
        elementInputDemoPage = new ElementInputDemoPage(page);
    }

    @BrowserTest
    public void testInput() {
        assertThat(elementInputDemoPage.getInput()).hasAttribute("placeholder", "Enter some text");

        elementInputDemoPage.getInput().fill("Hello World");
        assertThat(elementInputDemoPage.getInput()).hasValue("Hello World");
    }
}