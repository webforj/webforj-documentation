package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ToolbarPage extends BasePage {

    // Toolbar Themes Elements
    private final Locator dangerToolbar;
    private final Locator defaultToolbar;
    private final Locator grayToolbar;
    private final Locator infoToolbar;
    private final Locator primaryToolbar;
    private final Locator successToolbar;
    private final Locator warningToolbar;

    // Toolbar Compact Elements
    private final Locator compactToolbar;
    private final Locator tablerIcon;
    private final Locator applicationTitle;
    private final Locator salesTab;
    private final Locator enterpriseTab;
    private final Locator paymentTab;
    private final Locator historyTab;
    private final Locator mainTitle;
    private final Locator paragraph;

    // Toolbar Progress Bar Elements
    private final Locator progressBarToolbar;
    private final Locator progressBar;

    // Toolbar Slots Elements
    private final Locator slotsToolbar;
    private final Locator slotsTablerIcon;
    private final Locator slotsApplicationTitle;
    private final Locator toolbarTitle;
    private final Locator settingsButton;
    private final Locator profileButton;
    private final Locator slotsMainTitle;
    private final Locator slotsParagraph;

    /**
     * Constructor for ToolbarPage.
     *
     * @param page the Playwright Page object
     */
    public ToolbarPage(Page page) {
        super(page);

        // Toolbar Themes (dwc-id from ToolbarThemesIT)
        dangerToolbar = page.locator("dwc-toolbar[dwc-id='11']");
        defaultToolbar = page.locator("dwc-toolbar[dwc-id='16']");
        grayToolbar = page.locator("dwc-toolbar[dwc-id='21']");
        infoToolbar = page.locator("dwc-toolbar[dwc-id='26']");
        primaryToolbar = page.locator("dwc-toolbar[dwc-id='31']");
        successToolbar = page.locator("dwc-toolbar[dwc-id='36']");
        warningToolbar = page.locator("dwc-toolbar[dwc-id='41']");

        // Toolbar Compact (dwc-id from ToolbarCompactIT)
        compactToolbar = page.locator("dwc-toolbar[dwc-id='13']");
        tablerIcon = compactToolbar.locator("dwc-icon-button[dwc-id='15']");
        applicationTitle = compactToolbar.locator("h3[dwc-id='14']");
        salesTab = page.locator("dwc-tabbed-pane[dwc-id='17'] > dwc-tab").nth(0);
        enterpriseTab = page.locator("dwc-tabbed-pane[dwc-id='17'] > dwc-tab").nth(1);
        paymentTab = page.locator("dwc-tabbed-pane[dwc-id='17'] > dwc-tab").nth(2);
        historyTab = page.locator("dwc-tabbed-pane[dwc-id='17'] > dwc-tab").nth(3);
        mainTitle = page.locator("dwc-app-layout[dwc-id='10'] >> h1[dwc-id='11']");
        paragraph = page.locator("dwc-app-layout[dwc-id='10'] >>p[dwc-id='12']");

        // Toolbar Progress Bar (dwc-id from ToolbarProgressBarIT)
        progressBarToolbar = page.locator("dwc-toolbar[dwc-id='13']");
        progressBar = progressBarToolbar.locator("dwc-progressbar[dwc-id='16']");

        // Toolbar Slots (dwc-id from ToolbarSlotsIT)
        slotsToolbar = page.locator("dwc-toolbar[dwc-id='13']");
        slotsTablerIcon = slotsToolbar.locator("dwc-icon-button[dwc-id='15']");
        slotsApplicationTitle = slotsToolbar.locator("h3[dwc-id='14']");
        toolbarTitle = slotsToolbar.locator("h3[dwc-id='18']");
        settingsButton = slotsToolbar.locator("dwc-icon-button[dwc-id='16']");
        profileButton = slotsToolbar.locator("dwc-icon-button[dwc-id='17']");
        slotsMainTitle = page.locator("dwc-app-layout[dwc-id='10'] >> h1[dwc-id='11']");
        slotsParagraph = page.locator("dwc-app-layout[dwc-id='10'] >>p[dwc-id='12']");
    }

    // Toolbar Themes Getters
    public Locator getDangerToolbar() {
        return dangerToolbar;
    }

    public Locator getDefaultToolbar() {
        return defaultToolbar;
    }

    public Locator getGrayToolbar() {
        return grayToolbar;
    }

    public Locator getInfoToolbar() {
        return infoToolbar;
    }

    public Locator getPrimaryToolbar() {
        return primaryToolbar;
    }

    public Locator getSuccessToolbar() {
        return successToolbar;
    }

    public Locator getWarningToolbar() {
        return warningToolbar;
    }

    // Toolbar Compact Getters
    public Locator getCompactToolbar() {
        return compactToolbar;
    }

    public Locator getTablerIcon() {
        return tablerIcon;
    }

    public Locator getApplicationTitle() {
        return applicationTitle;
    }

    public Locator getSalesTab() {
        return salesTab;
    }

    public Locator getEnterpriseTab() {
        return enterpriseTab;
    }

    public Locator getPaymentTab() {
        return paymentTab;
    }

    public Locator getHistoryTab() {
        return historyTab;
    }

    public Locator getMainTitle() {
        return mainTitle;
    }

    public Locator getParagraph() {
        return paragraph;
    }

    // Toolbar Progress Bar Getters
    public Locator getProgressBarToolbar() {
        return progressBarToolbar;
    }

    public Locator getProgressBar() {
        return progressBar;
    }

    // Toolbar Slots Getters
    public Locator getSlotsToolbar() {
        return slotsToolbar;
    }

    public Locator getSlotsTablerIcon() {
        return slotsTablerIcon;
    }

    public Locator getSlotsApplicationTitle() {
        return slotsApplicationTitle;
    }

    public Locator getToolbarTitle() {
        return toolbarTitle;
    }

    public Locator getSettingsButton() {
        return settingsButton;
    }

    public Locator getProfileButton() {
        return profileButton;
    }

    public Locator getSlotsMainTitle() {
        return slotsMainTitle;
    }

    public Locator getSlotsParagraph() {
        return slotsParagraph;
    }
} 