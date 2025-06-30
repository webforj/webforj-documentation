package tests.ElementsTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ElementsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class InputFunctionIT extends BaseTest {

    private ElementsPage elements;

    @BeforeEach
    public void setupInputFunction() {
        page.navigate("https://docs.webforj.com/webforj/elementinputfunction?");
        elements = new ElementsPage(page);
    }

    @BrowserTest
    public void testInputFunction() {
        assertThat(elements.getDialogBox().locator("header")).hasText("Event listener");
        assertThat(elements.getDialogBox().locator("section")).hasText("Input click fired");

        elements.getOKButton().click();

        assertThat(elements.getSecondDialogBox().locator("header")).hasText("Asynchronous JavaScript function");
        assertThat(elements.getSecondDialogBox().locator("section"))
                .hasText("This message displays after programmatically clicking the input");
    }
} 