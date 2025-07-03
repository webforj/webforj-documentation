package tests.TextAreaTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TextAreaPage.PredictedTextAreaPage;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class PredictedTextDemoIT extends BaseTest {

    private PredictedTextAreaPage predictedTextAreaPage;

    @BeforeEach
    public void setupPredictedTextDemo() {
        navigateToRoute(PredictedTextAreaPage.getRoute());
        predictedTextAreaPage = new PredictedTextAreaPage(page);
    }

    @BrowserTest
    public void testPredictedTextDemo() {
        predictedTextAreaPage.getPredictedTextArea().fill("Sky is");
        page.waitForTimeout(3000);

        page.keyboard().press("Tab");

        assertThat(predictedTextAreaPage.getPredictedTextArea()).hasValue("Sky is the limit");

    }
}