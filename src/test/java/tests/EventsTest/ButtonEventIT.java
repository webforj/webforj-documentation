package tests.EventsTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ButtonEventIT extends BaseTest {

    @BeforeEach
    public void setupButtonEvent() {
        page.navigate("https://docs.webforj.com/webforj/buttonevent?");
    }
    
    @BrowserTest
    public void testButtonEvent(){
        Locator button = page.locator("dwc-button[dwc-id=\"11\"]");
        Locator counter = page.locator("div[dwc-id='13']");

        assertThat(counter).hasText("Current Counter: 0");

        button.click();
        assertThat(counter).hasText("Current Counter: 1");
    }
} 