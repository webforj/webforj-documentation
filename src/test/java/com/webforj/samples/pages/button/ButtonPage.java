package com.webforj.samples.pages.button;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.pages.BasePage;

public class ButtonPage extends BasePage {

    private static final String ROUTE = "button";

    private final Locator submitButton;
    private final Locator clearButton;
    private final Locator firstName;
    private final Locator lastName;
    private final Locator email;

    public ButtonPage(Page page) {
        super(page);


        this.firstName = page.getByLabel("First Name");
        this.lastName = page.getByLabel("Last Name");
        this.email = page.getByLabel(Pattern.compile("^E-?mail:?$"));

        this.submitButton = page.locator("dwc-button")
                .filter(new Locator.FilterOptions().setHasText("Submit"));
        this.clearButton = page.locator("dwc-button")
                .filter(new Locator.FilterOptions().setHasText("Clear"));
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getFirstName() {
        return firstName;
    }

    public Locator getLastName() {
        return lastName;
    }

    public Locator getEmail() {
        return email;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }

    public Locator getClearButton() {
        return clearButton;
    }
}