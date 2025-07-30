package tests.textarea;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.textarea.TextAreaPredictedTextPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TextAreaPredictedTextIT extends BaseTest {

    private TextAreaPredictedTextPage predictedTextAreaPage;

    @BeforeEach
    public void setupPredictedTextDemo() {
        navigateToRoute(TextAreaPredictedTextPage.getRoute());
        predictedTextAreaPage = new TextAreaPredictedTextPage(page);
    }

    @BrowserTest
    public void testPredictedTextDemo() {
        predictedTextAreaPage.getPredictedTextArea().fill("Sky is");
        page.waitForTimeout(3000);

        page.keyboard().press("Tab");

        assertThat(predictedTextAreaPage.getPredictedTextArea()).hasValue("Sky is the limit");

    }
}