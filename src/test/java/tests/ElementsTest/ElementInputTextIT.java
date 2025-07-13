package tests.ElementsTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.ElementsPage.ElementInputTextPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ElementInputTextIT extends BaseTest {

    private ElementInputTextPage elementInputTextPage;

    @BeforeEach
    public void setupElementInputText() {
        navigateToRoute(ElementInputTextPage.getRoute());
        elementInputTextPage = new ElementInputTextPage(page);
    }

    @BrowserTest
    public void testInputText() {
        assertThat(elementInputTextPage.getInput()).hasValue("Here is the set text");

        elementInputTextPage.getInput().clear();
        assertThat(elementInputTextPage.getInput()).hasValue("");
    }
}