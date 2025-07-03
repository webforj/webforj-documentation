package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage.SpinnerBasicsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerDemoViewIT extends BaseTest {

    private SpinnerBasicsPage spinnerPage;

    @BeforeEach
    public void setupSpinnerBasics() {
        page.navigate(SpinnerBasicsPage.getRoute());
        spinnerPage = new SpinnerBasicsPage(page);
    }

    @BrowserTest
    public void testHeaderIconsAndSpinnerVisible() {
        assertThat(spinnerPage.getCheckIcon1()).isVisible();
        assertThat(spinnerPage.getCheckIcon2()).isVisible();
        assertThat(spinnerPage.getBasicsSpinner()).isVisible();
    }
}