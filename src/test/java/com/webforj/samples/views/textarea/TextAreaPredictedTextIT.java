package com.webforj.samples.views.textarea;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.textarea.TextAreaPredictedTextPage;
import com.webforj.samples.views.BaseTest;

public class TextAreaPredictedTextIT extends BaseTest {

    private TextAreaPredictedTextPage predictedTextAreaPage;

    @BeforeEach
    public void setupPredictedTextDemo() {
        navigateToRoute(TextAreaPredictedTextPage.getRoute());
        predictedTextAreaPage = new TextAreaPredictedTextPage(page);
    }

    @Test
    public void testPredictedTextDemo() {
        predictedTextAreaPage.getPredictedTextArea().fill("Sky is");
        // Wait until suggestion applies on blur/tab
        page.keyboard().press("Tab");
        assertThat(predictedTextAreaPage.getPredictedTextArea()).hasValue("Sky is the limit");

    }
}
