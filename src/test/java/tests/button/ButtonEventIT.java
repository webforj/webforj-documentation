package tests.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.button.ButtonEventPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonEventIT extends BaseTest {

    private ButtonEventPage buttonEventPage;

    @BeforeEach
    public void setupButtonEvent() {
        navigateToRoute(ButtonEventPage.getRoute());
        buttonEventPage = new ButtonEventPage(page);
    }

    @BrowserTest
    public void testButtonEvent(){
        assertThat(buttonEventPage.getCounter()).hasText("Current Counter: 0");

        buttonEventPage.getButton().click();
        assertThat(buttonEventPage.getCounter()).hasText("Current Counter: 1");
    }
}