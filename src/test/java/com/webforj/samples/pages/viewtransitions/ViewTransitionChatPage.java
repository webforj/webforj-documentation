package com.webforj.samples.pages.viewtransitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ViewTransitionChatPage {

    private static final String ROUTE = "viewtransitionchat/";

    private final Locator chatToggleBtn;
    private final Locator chatCard;
    private final Locator chatClose;
    private final Locator chatName;
    private final Locator chatStatus;
    private final Locator chatGreeting;

    public ViewTransitionChatPage(Page page) {
        this.chatToggleBtn = page.locator(".chat-toggle-btn dwc-button");
        this.chatCard = page.locator(".chat-card");
        this.chatClose = page.locator(".chat-close");
        this.chatName = page.locator(".chat-name");
        this.chatStatus = page.locator(".chat-status");
        this.chatGreeting = page.locator(".chat-greeting");
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

    public Locator getChatName() {
        return chatName;
    }

    public Locator getChatStatus() {
        return chatStatus;
    }

    public Locator getChatGreeting() {
        return chatGreeting;
    }
}
