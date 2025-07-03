package tests.ElementsTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ElementsPage.ElementInputEventPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ElementInputEventIT extends BaseTest {

    private ElementInputEventPage elementInputEventPage;

    @BeforeEach
    public void setupInputEvent() {
        navigateToRoute(ElementInputEventPage.getRoute());
        elementInputEventPage = new ElementInputEventPage(page);
    }

    @BrowserTest
    public void testEventListener() {
        assertThat(elementInputEventPage.getLabel()).hasText("Enter Text and Press Enter");

        elementInputEventPage.getInput().fill("Hello World");
        elementInputEventPage.getInput().press("Enter");

        assertThat(elementInputEventPage.getDialogBox().locator("header")).hasText("Input Event");
        assertThat(elementInputEventPage.getDialogBox().locator("section")).hasText("Hello World");

        elementInputEventPage.getOKButton().click();
    }
}