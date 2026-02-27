package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AvatarExpansesPage {

    private static final String ROUTE = "avatarexpanses";

    private final Locator xxxsmallAvatar;
    private final Locator xxsmallAvatar;
    private final Locator xsmallAvatar;
    private final Locator smallAvatar;
    private final Locator mediumAvatar;
    private final Locator largeAvatar;
    private final Locator xlargeAvatar;
    private final Locator xxlargeAvatar;
    private final Locator xxxlargeAvatar;

    public AvatarExpansesPage(Page page) {
        this.xxxsmallAvatar = page.locator("dwc-avatar[expanse='3xs']");
        this.xxsmallAvatar = page.locator("dwc-avatar[expanse='2xs']");
        this.xsmallAvatar = page.locator("dwc-avatar[expanse='xs']");
        this.smallAvatar = page.locator("dwc-avatar[expanse='s']");
        this.mediumAvatar = page.locator("dwc-avatar[expanse='m']");
        this.largeAvatar = page.locator("dwc-avatar[expanse='l']");
        this.xlargeAvatar = page.locator("dwc-avatar[expanse='xl']");
        this.xxlargeAvatar = page.locator("dwc-avatar[expanse='2xl']");
        this.xxxlargeAvatar = page.locator("dwc-avatar[expanse='3xl']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getXxxsmallAvatar() {
        return xxxsmallAvatar;
    }

    public Locator getXxsmallAvatar() {
        return xxsmallAvatar;
    }

    public Locator getXsmallAvatar() {
        return xsmallAvatar;
    }

    public Locator getSmallAvatar() {
        return smallAvatar;
    }

    public Locator getMediumAvatar() {
        return mediumAvatar;
    }

    public Locator getLargeAvatar() {
        return largeAvatar;
    }

    public Locator getXlargeAvatar() {
        return xlargeAvatar;
    }

    public Locator getXxlargeAvatar() {
        return xxlargeAvatar;
    }

    public Locator getXxxlargeAvatar() {
        return xxxlargeAvatar;
    }
}