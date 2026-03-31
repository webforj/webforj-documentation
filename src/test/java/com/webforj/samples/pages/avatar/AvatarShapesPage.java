package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AvatarShapesPage {

    private static final String ROUTE = "avatarshapes";

    private final Locator circleAvatar;
    private final Locator squareAvatar;

    public AvatarShapesPage(Page page) {
        this.circleAvatar = page.locator("dwc-avatar[shape='circle']");
        this.squareAvatar = page.locator("dwc-avatar[shape='square']");
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getCircleAvatar() {
        return circleAvatar;
    }

    public Locator getSquareAvatar() {
        return squareAvatar;
    }
}
