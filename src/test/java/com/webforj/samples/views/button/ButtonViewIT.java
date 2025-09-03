package com.webforj.samples.views.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.button.ButtonPage;
import com.webforj.samples.views.BaseTest;

public class ButtonViewIT extends BaseTest {

    private ButtonPage button;

    @BeforeEach
    public void setupButtonDemo() {
        navigateToRoute(ButtonPage.getRoute());
        button = new ButtonPage(page);
    }

    @Test
    public void testClickSubmitDisplaysWelcomeMessage() {
        button.getSubmitButton().click();
        assertThat(page.locator("section")).hasText("Welcome to the app Jason Turner!");

    }

    @Test
    public void testClickClearClearsInputs() {
        assertThat(button.getFirstName()).hasValue("Jason");
        assertThat(button.getLastName()).hasValue("Turner");
        assertThat(button.getEmail()).hasValue("turner.jason@email.com");


        button.getClearButton().click();

        assertThat(button.getFirstName()).hasValue("");
        assertThat(button.getLastName()).hasValue("");
        assertThat(button.getEmail()).hasValue("");

    }
}