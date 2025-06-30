package tests.TextAreaTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TextAreaPage;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class PredictedTextDemoIT extends BaseTest {

    private TextAreaPage textAreaPage;

    @BeforeEach
    public void setupPredictedTextDemo() {
        page.navigate("https://docs.webforj.com/webforj/textareapredictedtext?");
        textAreaPage = new TextAreaPage(page);
    }

    @BrowserTest
    public void testPredictedTextDemo() {
        textAreaPage.getPredictedTextArea().fill("Sky is");
        page.waitForTimeout(3000);

        page.keyboard().press("Tab");

        assertThat(textAreaPage.getPredictedTextArea()).hasValue("Sky is the limit");

    }
} 