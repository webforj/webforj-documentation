package com.webforj.samples.pages.icon;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class IconVariationsPage extends BasePage {

    private static final String ROUTE = "iconvariations";

    private final Locator envelopeIcon;
    private final Locator solidEnvelopeIcon;
    private final Locator instagramIcon;
    private final Locator calendarIcon;
    private final Locator filledCalendarIcon;

    public IconVariationsPage(Page page) {
        super(page);

        envelopeIcon = page.getByRole(AriaRole.IMG,
                new Page.GetByRoleOptions().setName("envelope"));
        solidEnvelopeIcon = page.getByRole(AriaRole.IMG,
                new Page.GetByRoleOptions().setName("fas-envelope"));
        instagramIcon = page.getByRole(AriaRole.IMG,
                new Page.GetByRoleOptions().setName("fab instagram"));
        calendarIcon = page.getByRole(AriaRole.IMG,
                new Page.GetByRoleOptions().setName("calendar"));
        filledCalendarIcon = page.getByRole(AriaRole.IMG,
                new Page.GetByRoleOptions().setName("filled-calendar"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getEnvelopeIcon() {
        return envelopeIcon;
    }

    public Locator getSolidEnvelopeIcon() {
        return solidEnvelopeIcon;
    }

    public Locator getInstagramIcon() {
        return instagramIcon;
    }

    public Locator getCalendarIcon() {
        return calendarIcon;
    }

    public Locator getCalenderIcon() {
        return calendarIcon;
    }

    public Locator getFilledCalendarIcon() {
        return filledCalendarIcon;
    }

    public Locator getFilledCalenderIcon() {
        return filledCalendarIcon;
    }

    public Locator getEnvelopeIconSvg() {
        return envelopeIcon.locator("svg");
    }

    public Locator getSolidEnvelopeIconSvg() {
        return solidEnvelopeIcon.locator("svg");
    }

    public Locator getInstagramIconSvg() {
        return instagramIcon.locator("svg");
    }

    public Locator getCalendarIconSvg() {
        return calendarIcon.locator("svg");
    }

    public Locator getFilledCalendarIconSvg() {
        return filledCalendarIcon.locator("svg");
    }
}