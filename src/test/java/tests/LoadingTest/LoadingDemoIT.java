package tests.LoadingTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.LoadingPages.LoadingDemoPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LoadingDemoIT extends BaseTest {

    private LoadingDemoPage loadingDemoPage;

    @BeforeEach
    public void navigateToLoadingDemo() {
        navigateToRoute(LoadingDemoPage.getRoute());
        loadingDemoPage = new LoadingDemoPage(page);
    }

    @BrowserTest
    public void testLoadingComponent() {
        assertThat(loadingDemoPage.getLoadingComponent()).isVisible();
        assertThat(loadingDemoPage.getSpinnerText()).hasText("Loading... Please wait.");
    }

    @BrowserTest
    public void testIconVisibility() {
        assertThat(loadingDemoPage.getBookIcon()).isVisible();
        assertThat(loadingDemoPage.getYoutubeIcon()).isVisible();
    }
}