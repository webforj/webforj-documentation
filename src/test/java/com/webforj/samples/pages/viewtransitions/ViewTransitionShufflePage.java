package com.webforj.samples.pages.viewtransitions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ViewTransitionShufflePage {

    private static final String ROUTE = "viewtransitionshuffle/";

    private final Locator shuffleBtn;
    private final Locator shuffleList;
    private final Locator cards;

    public ViewTransitionShufflePage(Page page) {
        this.shuffleBtn = page.locator(".shuffle-btn");
        this.shuffleList = page.locator(".shuffle-list");
        this.cards = page.locator(".shuffle-card");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getShuffleBtn() {
        return shuffleBtn;
    }

    public Locator getShuffleList() {
        return shuffleList;
    }

    public Locator getCards() {
        return cards;
    }

    /**
     * Gets a card by its ID.
     * 
     * @param id the item ID (e.g. "1", "2")
     * @return the locator for the card
     */
    public Locator getCardById(String id) {
        return cards.filter(new Locator.FilterOptions()
                .setHas(cards.page().locator("css=[style*='view-transition-name: card-" + id + "']")));
    }
}
