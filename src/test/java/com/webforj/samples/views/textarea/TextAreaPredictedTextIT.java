package com.webforj.samples.views.textarea;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.textarea.TextAreaPredictedTextPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

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
