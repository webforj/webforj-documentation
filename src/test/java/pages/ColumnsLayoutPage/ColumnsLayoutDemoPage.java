package pages.ColumnsLayoutPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ColumnsLayoutDemoPage extends BasePage {

    private static final String ROUTE = "columnslayout";

    private final Locator dwcColumnsLayout;
    private final Locator firstName;
    private final Locator lastName;
    private final Locator email;
    private final Locator password;
    private final Locator confirmPassword;
    private final Locator stateDropdown;
    private final Locator zipCode;
    private final Locator cancelButton;
    private final Locator submitButton;

    public ColumnsLayoutDemoPage(Page page) {
        super(page);

        this.dwcColumnsLayout = page.locator("dwc-columns-layout");
        this.firstName = page.locator("dwc-field[dwc-id='12']");
        this.lastName = page.locator("dwc-field[dwc-id='13']");
        this.email = page.locator("dwc-field[dwc-id='14']");
        this.password = page.locator("dwc-field[dwc-id='15']");
        this.confirmPassword = page.locator("dwc-field[dwc-id='16']");
        this.stateDropdown = page.locator("dwc-dropdown[part='dropdown']");
        this.zipCode = page.locator("dwc-field[dwc-id='19']");
        this.cancelButton = page.locator("dwc-button[dwc-id='20']:has-text('Cancel')");
        this.submitButton = page.locator("dwc-button[dwc-id='17']:has-text('Submit')");
    }

    public static String getRoute() {
        return ROUTE;
    }

    public Locator getDwcColumnsLayout() {
        return dwcColumnsLayout;
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

    public Locator getPassword() {
        return password;
    }

    public Locator getConfirmPassword() {
        return confirmPassword;
    }

    public Locator getStateDropdown() {
        return stateDropdown;
    }

    public Locator getZipCode() {
        return zipCode;
    }

    public Locator getCancelButton() {
        return cancelButton;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }

    public void waitForAllElements() {
        firstName.waitFor();
        lastName.waitFor();
        email.waitFor();
        password.waitFor();
        confirmPassword.waitFor();
        stateDropdown.waitFor();
        zipCode.waitFor();
        cancelButton.waitFor();
        submitButton.waitFor();
    }
} 