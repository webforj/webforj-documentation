package com.webforj.samples.pages.googlecharts;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class ChartRedrawPage extends BasePage {

    private final Locator valueForInstagram;
    private final Locator valueForTwitter;
    private final Locator valueForFacebook;
    private final Locator valueForLinkedIn;
    private final Locator redrawChartButton;
    private final Locator instagramWarningMessage;
    private final Locator warningToast;

    private static final String ROUTE = "chartredraw";

    public ChartRedrawPage(Page page) {
        super(page);

        valueForInstagram = page.locator("dwc-field:has-text('Instagram')").locator("input");
        valueForTwitter = page.locator("dwc-field:has-text('Twitter')").locator("input");
        valueForFacebook = page.locator("dwc-field:has-text('Facebook')").locator("input");
        valueForLinkedIn = page.locator("dwc-field:has-text('LinkedIn')").locator("input");
        redrawChartButton = page.locator("dwc-button")
                .filter(new Locator.FilterOptions().setHasText("Redraw Chart"))
                .locator("[part~='control']");
        instagramWarningMessage = page.locator("dwc-alert.bbj-noborder");
        warningToast = page.locator("dwc-toast-group[placement='bottom']");
    }

    public void updateChart(int instagram, int twitter, int facebook, int linkedIn) {
        valueForInstagram.fill(String.valueOf(instagram));
        valueForTwitter.fill(String.valueOf(twitter));
        valueForFacebook.fill(String.valueOf(facebook));
        valueForLinkedIn.fill(String.valueOf(linkedIn));

        redrawChartButton.click();
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getValueForInstagram() {
        return valueForInstagram;
    }

    public Locator getValueForTwitter() {
        return valueForTwitter;
    }

    public Locator getValueForFacebook() {
        return valueForFacebook;
    }

    public Locator getValueForLinkedIn() {
        return valueForLinkedIn;
    }

    public Locator getRedrawChartButton() {
        return redrawChartButton;
    }

    public Locator getInstagramWarningMessage() {
        return instagramWarningMessage;
    }

    public Locator getWarningToast() {
        return warningToast;
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");
    }
}