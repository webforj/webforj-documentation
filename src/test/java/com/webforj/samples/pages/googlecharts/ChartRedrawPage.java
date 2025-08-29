package com.webforj.samples.pages.googlecharts;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class ChartRedrawPage extends BasePage {

    private final Locator valueForInstagram;
    private final Locator valueForTwitter;
    private final Locator valueForFacebook;
    private final Locator valueForLinkedIn;
    private final Locator redrawChartButton;
    private final Locator warningToast;

    private static final String ROUTE = "chartredraw";

    public ChartRedrawPage(Page page) {
        super(page);

        this.valueForInstagram = page.getByRole(AriaRole.SPINBUTTON,
                new Page.GetByRoleOptions().setName("Value for Instagram"));
        this.valueForTwitter = page.getByRole(AriaRole.SPINBUTTON,
                new Page.GetByRoleOptions().setName("Value for Twitter"));
        this.valueForFacebook = page.getByRole(AriaRole.SPINBUTTON,
                new Page.GetByRoleOptions().setName("Value for Facebook"));
        this.valueForLinkedIn = page.getByRole(AriaRole.SPINBUTTON,
                new Page.GetByRoleOptions().setName("Value for LinkedIn"));
        this.redrawChartButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Redraw Chart"));

        this.warningToast = page.getByRole(AriaRole.ALERT, new Page.GetByRoleOptions()
                .setName(Pattern.compile("^Enter a valid number between 1 and 1000000", Pattern.CASE_INSENSITIVE)));
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

    public Locator getWarningToast() {
        return warningToast;
    }

    public void cleanField(Locator locator) {
        locator.click();
        page.keyboard().press("Control+A");
        page.keyboard().press("Backspace");
    }
}