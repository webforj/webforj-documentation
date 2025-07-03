package pages.ColumnsLayoutPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class ColumnsLayoutFormPage extends BasePage {

    private static final String ROUTE = RouteConfig.COLUMNS_LAYOUT_FORM;

    private final Locator dwcColumnsLayout;
    private final Locator firstName;
    private final Locator lastName;
    private final Locator email;
    private final Locator password;
    private final Locator confirmPassword;
    private final Locator stateDropdown;
    private final Locator zipCode;
    private final Locator cancelButton;
    private final Locator submitButtonLayoutForm;

    public ColumnsLayoutFormPage(Page page) {
        super(page);
        this.dwcColumnsLayout = page.locator("dwc-columns-layout");
        this.firstName = page.locator("dwc-field:has-text('First Name')");
        this.lastName = page.locator("dwc-field:has-text('Last Name')");
        this.email = page.locator("dwc-field:has-text('Email')");
        this.password = page.locator("dwc-field:has-text('Password')", new Page.LocatorOptions().setHasNotText("Confirm"));
        this.confirmPassword = page.locator("dwc-field:has-text('Confirm Password')");
        this.stateDropdown = page.locator("dwc-dropdown[part='dropdown']");
        this.zipCode = page.locator("dwc-field:has-text('Zip')");
        this.cancelButton = page.locator("dwc-button:has-text('Cancel')");
        this.submitButtonLayoutForm = page.locator("dwc-button:has-text('Submit')");
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

    public Locator getSubmitButtonLayoutForm() {
        return submitButtonLayoutForm;
    }
}