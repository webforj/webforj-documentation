package tests.LoadingTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.LoadingPages.LoadingSpinnerPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class LoadingSpinnerIT extends BaseTest {

    private LoadingSpinnerPage loadingSpinnerPage;

    @BeforeEach
    public void setupLoadingSpinner() {
        navigateToRoute(LoadingSpinnerPage.getRoute());
        loadingSpinnerPage = new LoadingSpinnerPage(page);
    }

    @BrowserTest
    public void testLoadingSpinner() {
        assertThat(loadingSpinnerPage.getLoading()).isVisible();
    }
}