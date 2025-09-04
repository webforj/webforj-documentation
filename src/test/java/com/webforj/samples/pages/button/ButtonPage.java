package com.webforj.samples.pages.button;

import java.util.regex.Pattern;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.samples.pages.BasePage;

public class ButtonPage extends BasePage {

    private static final String ROUTE = "button";

    private final Locator submitButton;
    private final Locator clearButton;
    private final Locator firstName;
    private final Locator lastName;
    private final Locator email;
    private final Locator welcomeDialog;

    public ButtonPage(Page page) {
        super(page);


        this.firstName = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name"));
        this.lastName = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name"));
        this.email = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("E-mail:"));

        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        this.clearButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Clear"));

        this.welcomeDialog = page.getByText("Welcome to the app Jason");
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

    public Locator getWelcomeDialog() {
        return welcomeDialog;
    }
}