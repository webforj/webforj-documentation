package com.webforj.samples.views.viewtransitions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.webforj.samples.pages.viewtransitions.ViewTransitionChatPage;
import com.webforj.samples.utils.WaitUtils;
import com.webforj.samples.views.BaseTest;

class ViewTransitionChatViewIT extends BaseTest {

    private ViewTransitionChatPage chatPage;

    @BeforeEach
    void setupChatDemo() {
        navigateToRoute(ViewTransitionChatPage.getRoute());
        WaitUtils.disableAnimations(page);
        chatPage = new ViewTransitionChatPage(page);
    }

    @Test
    @DisplayName("Should toggle chat widget visibility")
    void shouldToggleChat() {
        assertThat(chatPage.getChatCard()).isVisible();

        chatPage.getChatClose().click();
        assertThat(chatPage.getChatCard()).isHidden();

        chatPage.getChatToggleBtn().click();
        assertThat(chatPage.getChatCard()).isVisible();
        assertThat(chatPage.getChatName()).hasText("Support Team");
        assertThat(chatPage.getChatStatus()).hasText("Online");
        assertThat(chatPage.getChatGreeting()).hasText("👋 Hi there!");

        chatPage.getChatToggleBtn().click();
        assertThat(chatPage.getChatCard()).isHidden();
    }
}
