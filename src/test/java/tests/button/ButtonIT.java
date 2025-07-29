package tests.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.button.ButtonPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonIT extends BaseTest {

    private ButtonPage buttonDemoPage;

    @BeforeEach
    public void setupButtonDemo() {
        navigateToRoute(ButtonPage.getRoute());
        buttonDemoPage = new ButtonPage(page);
    }

    @BrowserTest
    public void testClickSubmitDisplaysWelcomeMessage() {
        buttonDemoPage.getSubmitButton().click();
        assertThat(page.locator("section")).hasText("Welcome to the app Jason Turner!");

    }

    @BrowserTest
    public void testClickClearClearsInputs() {
        assertThat(buttonDemoPage.getFirstName()).hasValue("Jason");
        assertThat(buttonDemoPage.getLastName()).hasValue("Turner");
        assertThat(buttonDemoPage.getEmail()).hasValue("turner.jason@email.com");


        buttonDemoPage.getClearButton().click();

        assertThat(buttonDemoPage.getFirstName()).hasValue("");
        assertThat(buttonDemoPage.getLastName()).hasValue("");
        assertThat(buttonDemoPage.getEmail()).hasValue("");

    }
}