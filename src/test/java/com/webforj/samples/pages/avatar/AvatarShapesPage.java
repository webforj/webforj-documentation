package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AvatarShapesPage {

    private static final String ROUTE = "avatarshapes";

    private final Locator circleAvatar;
    private final Locator squareAvatar;

    public AvatarShapesPage(Page page) {
        this.circleAvatar = page.locator("dwc-avatar[shape='circle']");
        this.squareAvatar = page.locator("dwc-avatar[shape='square']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getCircleAvatar() {
        return circleAvatar;
    }

    public Locator getSquareAvatar() {
        return squareAvatar;
    }
}