package pages.ColumnsLayoutPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class ColumnsLayoutFormPage extends BasePage {

    private static final String ROUTE = "columnslayoutform";

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
        this.firstName = page.locator("dwc-field[dwc-id='12']");
        this.lastName = page.locator("dwc-field[dwc-id='13']");
        this.email = page.locator("dwc-field[dwc-id='14']");
        this.password = page.locator("dwc-field[dwc-id='15']");
        this.confirmPassword = page.locator("dwc-field[dwc-id='16']");
        this.stateDropdown = page.locator("dwc-dropdown[part='dropdown']");
        this.zipCode = page.locator("dwc-field[dwc-id='19']");
        this.cancelButton = page.locator("dwc-button[dwc-id='20']:has-text('Cancel')");
        this.submitButtonLayoutForm = page.locator("dwc-button[dwc-id='21']:has-text('Submit')");
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