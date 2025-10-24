package com.webforj.samples.views.button;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.button.ButtonEventPage;
import com.webforj.samples.views.BaseTest;

public class ButtonEventViewIT extends BaseTest {

    private ButtonEventPage buttonEventPage;

    @BeforeEach
    public void setupButtonEventDemo() {
        navigateToRoute(ButtonEventPage.getRoute());
        buttonEventPage = new ButtonEventPage(page);
    }

    @Test
    public void testButtonEventIsTriggeredWhenButtonIsClicked() {
        buttonEventPage.getButton().click();
        assertThat(buttonEventPage.getCounterText("1")).isVisible();
    }

}
