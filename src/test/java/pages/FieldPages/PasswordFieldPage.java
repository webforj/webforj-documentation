package pages.FieldPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class PasswordFieldPage extends BasePage {

    private final Locator passwordField;
    private final Locator eyeOffIcon;

    public PasswordFieldPage(Page page) {
        super(page);
        passwordField = page.locator("dwc-field[dwc-id='11'] input");
        eyeOffIcon = page.locator("dwc-icon-button[part='eye-off-icon']");
    }

    public Locator getPasswordField() {
        return passwordField;
    }

    public Locator getEyeOffIcon() {
        return eyeOffIcon;
    }
} 