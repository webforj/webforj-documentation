package tests.SpinnerTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.SpinnerPage.SpinnerDirectionPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class SpinnerDirectionDemoViewIT extends BaseTest {

    private SpinnerDirectionPage spinnerPage;

    @BeforeEach
    public void setupSpinnerDirection() {
        page.navigate(SpinnerDirectionPage.getRoute());
        spinnerPage = new SpinnerDirectionPage(page);
    }

    @BrowserTest
    public void testSpinnerDirectionCorrectness() {
        spinnerPage.getClockwiseButton().click();
        assertThat(spinnerPage.getSpinner11()).hasAttribute("clockwise", "");

        spinnerPage.getCounterClockwiseButton().click();
        assertThat(spinnerPage.getSpinner11()).not().hasAttribute("clockwise", "");
    }
}