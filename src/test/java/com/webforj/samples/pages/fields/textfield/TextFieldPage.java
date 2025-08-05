package com.webforj.samples.pages.fields.textfield;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class TextFieldPage extends BasePage {

    private static final String ROUTE = "textfield";

    private final Locator usernameInput;
    private final Locator emailInput;
    private final Locator phoneInput;
    private final Locator urlInput;
    private final Locator searchInput;
    private final Locator alertPopover;

    public TextFieldPage(Page page) {
        super(page);
        usernameInput = page.locator("dwc-field:has-text('Enter Name') >> input");
        emailInput = page.locator("dwc-field:has-text('Enter Email') >> input");
        phoneInput = page.locator("dwc-field:has-text('Enter Phone Number') >> input");
        urlInput = page.locator("dwc-field:has-text('Enter URL') >> input");
        searchInput = page.locator("dwc-field:has-text('Enter Your Search') >> input");
        alertPopover = page.locator("div[class*='dwc-positioner']");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getUsernameInput() {
        return usernameInput;
    }

    public Locator getEmailInput() {
        return emailInput;
    }

    public Locator getPhoneInput() {
        return phoneInput;
    }

    public Locator getUrlInput() {
        return urlInput;
    }

    public Locator getSearchInput() {
        return searchInput;
    }

    public Locator getAlertPopover() {
        return alertPopover;
    }
}