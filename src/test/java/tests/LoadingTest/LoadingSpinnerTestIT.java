package tests.LoadingTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LoadingSpinnerTestIT extends BaseTest {

    @BeforeEach
    public void setupLoadingSpinner() {
        page.navigate("https://docs.webforj.com/loadingspinnerdemo?");
    }

    @BrowserTest
    public void testLoadingSpinner() {
        Locator loading = page.locator("dwc-loading[dwc-id='12']");
        assertThat(loading).isVisible();
    }
} 