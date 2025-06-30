package tests.ButtonTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import pages.ButtonPage.ButtonDemoPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonDemoIT extends BaseTest {

    private ButtonDemoPage buttonPage;

    @BeforeEach
    public void setupButtonDemo() {
        navigateToRoute(ButtonDemoPage.getRoute());
        buttonPage = new ButtonDemoPage(page);
    }

    @BrowserTest
    public void testClickSubmitDisplaysWelcomeMessage() {
        buttonPage.getSubmitButton().click();
        assertThat(page.locator("section")).hasText("Welcome to the app Jason Turner!");

    }

    @BrowserTest
    public void testClickClearClearsInputs() {
        Locator firstName = page.locator("dwc-field[dwc-id='12'] >> input");
        Locator lastName = page.locator("dwc-field[dwc-id='13'] >> input");
        Locator email = page.locator("dwc-field[dwc-id='15'] >> input");

        assertThat(firstName).hasValue("Jason");
        assertThat(lastName).hasValue("Turner");
        assertThat(email).hasValue("turner.jason@email.com");

        buttonPage.getClearButton().click();

        assertThat(firstName).hasValue("");
        assertThat(lastName).hasValue("");
        assertThat(email).hasValue("");

    }
} 