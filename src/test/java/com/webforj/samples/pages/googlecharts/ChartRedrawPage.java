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
    private final Locator warningMessage;
    private final Locator warningToast;
    private final Locator title;

    private static final String ROUTE = "chartredraw";

    public ChartRedrawPage(Page page) {
        super(page);
        valueForInstagram = page.locator("dwc-field:has-text('Instagram') >> input");
        valueForTwitter = page.locator("dwc-field:has-text('Twitter') >> input");
        valueForFacebook = page.locator("dwc-field:has-text('Facebook') >> input");
        valueForLinkedIn = page.locator("dwc-field:has-text('LinkedIn') >> input");
        redrawChartButton = page.locator("dwc-button:has-text('Redraw Chart')");
        warningMessage = page.locator("dwc-alert.bbj-noborder");
        warningToast = page.locator("dwc-toast-group[placement='bottom']");
        title = page.locator("text:has-text('Social Media Following')");
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

    public Locator getWarningMessage() {
        return warningMessage;
    }

    public Locator getWarningToast(){
        return warningToast;
    }

    public Locator getTitle() {
        return title;
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");
    }
}