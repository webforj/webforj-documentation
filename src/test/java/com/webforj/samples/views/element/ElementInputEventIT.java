package com.webforj.samples.views.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        elementInputEventPage.getInputField().fill("Hello World");
        elementInputEventPage.getInputField().press("Enter");

        assertThat(elementInputEventPage.getHeader()).hasText("Input Event");
        assertThat(elementInputEventPage.getSection()).hasText("Hello World");

        elementInputEventPage.getOKButton().click();
    }
}
