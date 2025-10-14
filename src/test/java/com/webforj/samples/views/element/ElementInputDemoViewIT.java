package com.webforj.samples.views.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.element.ElementInputDemoPage;
import com.webforj.samples.views.BaseTest;

public class ElementInputDemoViewIT extends BaseTest {

    private ElementInputDemoPage elementInputDemoPage;

    @BeforeEach
    public void setupInputDemo() {
        navigateToRoute(ElementInputDemoPage.getRoute());
        elementInputDemoPage = new ElementInputDemoPage(page);
    }

    @Test
    public void testInputValue() {
        elementInputDemoPage.getInputField().fill("Hello World");
        assertThat(elementInputDemoPage.getInputField()).hasValue("Hello World");
    }
}
