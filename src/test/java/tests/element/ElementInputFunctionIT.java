package tests.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.element.ElementInputFunctionPage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class ElementInputFunctionIT extends BaseTest {

    private ElementInputFunctionPage elementInputFunctionPage;

    @BeforeEach
    public void setupInputFunction() {
        navigateToRoute(ElementInputFunctionPage.getRoute());
        elementInputFunctionPage = new ElementInputFunctionPage(page);
    }

    @BrowserTest
    public void testInputFunction() {
        WaitUtil.waitForVisible(elementInputFunctionPage.getDialogBox(), 10000);
        assertThat(elementInputFunctionPage.getDialogBox().locator("header")).hasText("Event listener");
        assertThat(elementInputFunctionPage.getDialogBox().locator("section")).hasText("Input click fired");

        elementInputFunctionPage.getOKButton().click();

        assertThat(elementInputFunctionPage.getSecondDialogBox().locator("header")).hasText("Asynchronous JavaScript function");
        assertThat(elementInputFunctionPage.getSecondDialogBox().locator("section"))
                .hasText("This message displays after programmatically clicking the input");
    }
}