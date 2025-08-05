package com.webforj.samples.views.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.webforj.samples.pages.element.ElementInputDemoPage;
import com.webforj.samples.utils.annotations.BrowserTest;
import com.webforj.samples.views.BaseTest;

public class ElementInputDemoIT extends BaseTest {

    private ElementInputDemoPage elementInputDemoPage;

    @BeforeEach
    public void setupInputDemo() {
        navigateToRoute(ElementInputDemoPage.getRoute());
        elementInputDemoPage = new ElementInputDemoPage(page);
    }

    @BrowserTest
    public void testInput() {
        elementInputDemoPage.getInput().fill("Hello World");
        assertThat(elementInputDemoPage.getInput()).hasValue("Hello World");
    }
}
