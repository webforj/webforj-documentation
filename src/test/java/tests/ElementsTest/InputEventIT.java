package tests.ElementsTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ElementsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class InputEventIT extends BaseTest {

    private ElementsPage elements;

    @BeforeEach
    public void setupInputEvent() {
        page.navigate("https://docs.webforj.com/webforj/elementinputevent?");
        elements = new ElementsPage(page);
    }

    @BrowserTest
    public void testEventListener() {
        assertThat(elements.getLabel()).hasText("Enter Text and Press Enter");

        elements.getInput().fill("Hello World");
        elements.getInput().press("Enter");

        assertThat(elements.getDialogBox().locator("header")).hasText("Input Event");
        assertThat(elements.getDialogBox().locator("section")).hasText("Hello World");

        elements.getOKButton().click();
    }
} 