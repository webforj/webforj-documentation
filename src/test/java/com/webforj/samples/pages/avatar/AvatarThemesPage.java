package com.webforj.samples.pages.avatar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.SupportedLanguage;

public class AvatarThemesPage {

    private static final String ROUTE = "avatarthemes";

    private final Locator defaultAvatar;
    private final Locator grayAvatar;
    private final Locator primaryAvatar;
    private final Locator successAvatar;
    private final Locator warningAvatar;
    private final Locator dangerAvatar;
    private final Locator infoAvatar;

    public AvatarThemesPage(Page page) {
        this.defaultAvatar = page.locator("dwc-avatar[theme='default']").first();
        this.grayAvatar = page.locator("dwc-avatar[theme='gray']").first();
        this.primaryAvatar = page.locator("dwc-avatar[theme='primary']").first();
        this.successAvatar = page.locator("dwc-avatar[theme='success']").first();
        this.warningAvatar = page.locator("dwc-avatar[theme='warning']").first();
        this.dangerAvatar = page.locator("dwc-avatar[theme='danger']").first();
        this.infoAvatar = page.locator("dwc-avatar[theme='info']").first();
    }

    public static String getRoute(SupportedLanguage language) {
        return language.getPath(ROUTE);
    }

    public Locator getDefaultAvatar() {
        return defaultAvatar;
    }

    public Locator getGrayAvatar() {
        return grayAvatar;
    }

    public Locator getPrimaryAvatar() {
        return primaryAvatar;
    }

    public Locator getSuccessAvatar() {
        return successAvatar;
    }

    public Locator getWarningAvatar() {
        return warningAvatar;
    }

    public Locator getDangerAvatar() {
        return dangerAvatar;
    }

    public Locator getInfoAvatar() {
        return infoAvatar;
    }
}
