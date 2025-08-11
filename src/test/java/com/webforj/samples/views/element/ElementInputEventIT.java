package com.webforj.samples.views.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.options.WaitUntilState;
import com.webforj.samples.pages.element.ElementInputEventPage;
import com.webforj.samples.views.BaseTest;

public class ElementInputEventIT extends BaseTest {

    private ElementInputEventPage elementInputEventPage;

    @BeforeEach
    public void setupInputEvent() {
        navigateToRoute(ElementInputEventPage.getRoute());
        elementInputEventPage = new ElementInputEventPage(page);
    }

    @Test
    public void testEventListener() {
        elementInputEventPage.getInput().fill("Hello World");
        elementInputEventPage.getInput().press("Enter");

        assertThat(elementInputEventPage.getDialogBox().locator("header")).hasText("Input Event");
        assertThat(elementInputEventPage.getDialogBox().locator("section")).hasText("Hello World");

        elementInputEventPage.getOKButton().click();
    }
}
