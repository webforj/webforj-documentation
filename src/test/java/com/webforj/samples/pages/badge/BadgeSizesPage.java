package com.webforj.samples.pages.badge;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BadgeSizesPage {

    private static final String ROUTE = "badgesizes";

    private final Locator xsmallBadge;
    private final Locator mediumBadge;
    private final Locator xlargeBadge;
    private final Locator circularBadge;

    public BadgeSizesPage(Page page) {
        this.xsmallBadge = page.locator("dwc-badge").filter(
                new Locator.FilterOptions().setHasText("xs"));
        this.mediumBadge = page.locator("dwc-badge").filter(
                new Locator.FilterOptions().setHasText("m"));
        this.xlargeBadge = page.locator("dwc-badge").filter(
                new Locator.FilterOptions().setHasText("xl")).first();
        this.circularBadge = page.locator("dwc-badge").filter(
                new Locator.FilterOptions().setHasText("5")).first();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getXsmallBadge() {
        return xsmallBadge;
    }

    public Locator getMediumBadge() {
        return mediumBadge;
    }

    public Locator getXlargeBadge() {
        return xlargeBadge;
    }

    public Locator getCircularBadge() {
        return circularBadge;
    }
}
