package com.webforj.samples.views.textarea;

import com.webforj.samples.pages.textarea.TextAreaStatesPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.Test;

        public class TextAreaStatesViewIT extends BaseTest {

    private TextAreaStatesPage textAreaStatesPage;

    @BeforeEach
    public void setupTextAreaStatesPage() {
        navigateToRoute(TextAreaStatesPage.getRoute());
        textAreaStatesPage = new TextAreaStatesPage(page);
    }

    @Test
    public void testReadonlyAreaIsReadonly() {
        assertThat(textAreaStatesPage.getReadonlyArea()).hasAttribute("readonly", "");
    }

    @Test
    public void testDisabledAreaIsDisabled() {
        assertThat(textAreaStatesPage.getDisabledArea()).isDisabled();
    }
}
