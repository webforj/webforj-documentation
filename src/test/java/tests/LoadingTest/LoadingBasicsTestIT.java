package tests.LoadingTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LoadingBasicsTestIT extends BaseTest {

    @BeforeEach
    public void navigateToLoadingDemo() {
        page.navigate("https://docs.webforj.com/loadingdemo?");
    }

    @BrowserTest
    public void testLoadingComponent() {
        Locator loadingComponent = page.locator("dwc-spinner[theme='primary']");
        assertThat(loadingComponent).isVisible();

        Locator spinnerText = page.locator("focus-trap");
        assertThat(spinnerText).hasText("Loading... Please wait.");
    }

    @BrowserTest
    public void testIconVisibility() {
        Locator bookIcon = page.locator("dwc-icon.book");
        assertThat(bookIcon).isVisible();

        Locator youtubeIcon = page.locator("dwc-icon.youtube");
        assertThat(youtubeIcon).isVisible();

    }
} 