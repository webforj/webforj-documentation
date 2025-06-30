package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class TextFieldPage extends BasePage {

    private final Locator usernameInput;
    private final Locator emailInput;
    private final Locator phoneInput;
    private final Locator urlInput;
    private final Locator searchInput;
    private final Locator alertPopover;

    public TextFieldPage(Page page) {
        super(page);
        usernameInput = page.locator("dwc-field[dwc-id='11'] >> input");
        emailInput = page.locator("dwc-field[dwc-id='12'] >> input");
        phoneInput = page.locator("dwc-field[dwc-id='13'] >> input");
        urlInput = page.locator("dwc-field[dwc-id='14'] >> input");
        searchInput = page.locator("dwc-field[dwc-id='15'] >> input");
        alertPopover = page.locator("div[class*='dwc-positioner']");
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