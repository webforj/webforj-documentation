package pages.DialogPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class DialogThemesPage extends BasePage {

    private static final String ROUTE = "dialogthemesview";

    // DialogThemes locators
    private final Locator dialog;
    private final Locator themeChoiceBox;
    private final Locator dialogHeader;
    private final Locator dialogContent;
    private final Locator selectAlignmentButton;
    private final Locator dangerTheme;
    private final Locator defaultTheme;
    private final Locator grayTheme;
    private final Locator infoTheme;
    private final Locator primaryTheme;
    private final Locator successTheme;
    private final Locator warningTheme;

    public DialogThemesPage(Page page) {
        super(page);
        
        // Initialize DialogThemes locators
        dialog = page.locator("dwc-dialog");
        themeChoiceBox = page.locator("dwc-choicebox[label='Select Theme']");
        dialogHeader = page.locator("div[part='header']");
        dialogContent = page.locator("div[part='content']");
        selectAlignmentButton = page.locator("dwc-dropdown[part='dropdown']");
        dangerTheme = page.locator("text=Danger");
        defaultTheme = page.locator("text=Default");
        grayTheme = page.locator("text=Gray");
        infoTheme = page.locator("text=Info");
        primaryTheme = page.locator("text=Primary");
        successTheme = page.locator("text=Success");
        warningTheme = page.locator("text=Warning");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // DialogThemes getters
    public Locator getDialog() {
        return dialog;
    }

    public Locator getThemeChoiceBox() {
        return themeChoiceBox;
    }

    public Locator getDialogHeader() {
        return dialogHeader;
    }

    public Locator getDialogContent() {
        return dialogContent;
    }

    public Locator getSelectAlignmentButton() {
        return selectAlignmentButton;
    }

    public Locator getDangerTheme() {
        return dangerTheme;
    }

    public Locator getDefaultTheme() {
        return defaultTheme;
    }

    public Locator getGrayTheme() {
        return grayTheme;
    }

    public Locator getInfoTheme() {
        return infoTheme;
    }

    public Locator getPrimaryTheme() {
        return primaryTheme;
    }

    public Locator getSuccessTheme() {
        return successTheme;
    }

    public Locator getWarningTheme() {
        return warningTheme;
    }
} 