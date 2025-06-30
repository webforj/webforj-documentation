package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerBasicsIT extends BaseTest {

    private SpinnerPage spinnerPage;

    @BeforeEach
    public void setupSpinnerBasics() {
        page.navigate("https://docs.webforj.com/webforj/spinnerdemo?");
        spinnerPage = new SpinnerPage(page);
    }

    @BrowserTest
    public void testHeaderIconsAndSpinnerVisible() {
        assertThat(spinnerPage.getCheckIcon1()).isVisible();
        assertThat(spinnerPage.getCheckIcon2()).isVisible();
        assertThat(spinnerPage.getBasicsSpinner()).isVisible();
    }
} 