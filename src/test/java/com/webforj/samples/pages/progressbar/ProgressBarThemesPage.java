package com.webforj.samples.pages.progressbar;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProgressBarThemesPage {

    private static final String ROUTE = "progressbarthemes";

    private final Locator dangerBar;
    private final Locator defaultBar;
    private final Locator grayBar;
    private final Locator infoBar;
    private final Locator primaryBar;
    private final Locator successBar;
    private final Locator warningBar;

    public ProgressBarThemesPage(Page page) {

        dangerBar = page.locator("dwc-progressbar[theme='danger']");
        defaultBar = page.locator("dwc-progressbar[theme='default']");
        grayBar = page.locator("dwc-progressbar[theme='gray']");
        infoBar = page.locator("dwc-progressbar[theme='info']");
        primaryBar = page.locator("dwc-progressbar[theme='primary']");
        successBar = page.locator("dwc-progressbar[theme='success']");
        warningBar = page.locator("dwc-progressbar[theme='warning']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDangerBar() {
        return dangerBar;
    }

    public Locator getDefaultBar() {
        return defaultBar;
    }

    public Locator getGrayBar() {
        return grayBar;
    }

    public Locator getInfoBar() {
        return infoBar;
    }

    public Locator getPrimaryBar() {
        return primaryBar;
    }

    public Locator getSuccessBar() {
        return successBar;
    }

    public Locator getWarningBar() {
        return warningBar;
    }
}