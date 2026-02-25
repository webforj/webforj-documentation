package com.webforj.samples.pages.viewtransitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ViewTransitionChatPage {

    private static final String ROUTE = "viewtransitionchat/";

    private final Locator chatToggleBtn;
    private final Locator chatCard;
    private final Locator chatClose;

    public ViewTransitionChatPage(Page page) {
        this.chatToggleBtn = page.locator(".chat-toggle-btn dwc-button");
        this.chatCard = page.locator(".chat-card");
        this.chatClose = page.locator(".chat-close");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getChatToggleBtn() {
        return chatToggleBtn;
    }

    public Locator getChatCard() {
        return chatCard;
    }

    public Locator getChatClose() {
        return chatClose;
    }
}
