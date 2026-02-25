package com.webforj.samples.views.viewtransitions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.viewtransitions.ViewTransitionChatPage;
import com.webforj.samples.views.BaseTest;

class ViewTransitionChatViewIT extends BaseTest {

    private ViewTransitionChatPage chatPage;

    @BeforeEach
    void setupChatDemo() {
        navigateToRoute(ViewTransitionChatPage.getRoute());
        chatPage = new ViewTransitionChatPage(page);
    }

    @Test
    @DisplayName("Should toggle chat widget visibility")
    void shouldToggleChat() {
        assertThat(chatPage.getChatCard()).isVisible();

        chatPage.getChatClose().click();
        assertThat(chatPage.getChatCard()).isHidden();

        page.waitForTimeout(500);

        chatPage.getChatToggleBtn().dispatchEvent("click");
        assertThat(chatPage.getChatCard()).isVisible();

        chatPage.getChatToggleBtn().dispatchEvent("click");
        assertThat(chatPage.getChatCard()).isHidden();
    }
}
