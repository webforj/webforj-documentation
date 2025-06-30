package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SplitterPage extends BasePage {

    // SplitterBasics and common elements
    private final Locator splitter;
    private final Locator splitterIcon;
    private final Locator masterPanel;
    private final Locator detailPanel;

    // SplitterOrientation
    private final Locator horizontalSplitter;
    private final Locator verticalSplitter;

    // SplitterNested
    private final Locator nestedSplitter;
    private final Locator nestedMasterPanel;
    private final Locator nestedDetailPanel;

    // SplitterMaxMin
    private final Locator masterPanelWithConstraints;
    private final Locator detailPanelWithConstraints;

    // SplitterPosition
    private final Locator positionedMasterPanel;
    private final Locator positionedDetailPanel;

    public SplitterPage(Page page) {
        super(page);

        // SplitterBasics and common elements
        splitter = page.locator("dwc-splitter[dwc-id='11']");
        splitterIcon = page.locator("dwc-splitter[dwc-id='11'] >> div > dwc-icon");
        masterPanel = page.locator("dwc-splitter >> div[dwc-id='12']");
        detailPanel = page.locator("dwc-splitter >> div[dwc-id='13']");

        // SplitterOrientation
        horizontalSplitter = page.locator("dwc-splitter[orientation='horizontal']");
        verticalSplitter = page.locator("dwc-splitter[orientation='vertical']");

        // SplitterNested
        nestedSplitter = page.locator("dwc-splitter >> dwc-splitter[dwc-id='13']");
        nestedMasterPanel = page.locator("dwc-splitter >> dwc-splitter >> div[dwc-id='14']");
        nestedDetailPanel = page.locator("dwc-splitter >> dwc-splitter >> div[dwc-id='15']");

        // SplitterMaxMin
        masterPanelWithConstraints = page.locator("dwc-splitter >> div[dwc-id='12']");
        detailPanelWithConstraints = page.locator("dwc-splitter >> div[dwc-id='13']");

        // SplitterPosition
        positionedMasterPanel = page.locator("dwc-splitter >> div[dwc-id='12']");
        positionedDetailPanel = page.locator("dwc-splitter >> div[dwc-id='13']");
    }

    // SplitterBasics and common elements getters
    public Locator getSplitter() {
        return splitter;
    }

    public Locator getSplitterIcon() {
        return splitterIcon;
    }

    public Locator getMasterPanel() {
        return masterPanel;
    }

    public Locator getDetailPanel() {
        return detailPanel;
    }

    // SplitterOrientation getters
    public Locator getHorizontalSplitter() {
        return horizontalSplitter;
    }

    public Locator getVerticalSplitter() {
        return verticalSplitter;
    }

    // SplitterNested getters
    public Locator getNestedSplitter() {
        return nestedSplitter;
    }

    public Locator getNestedMasterPanel() {
        return nestedMasterPanel;
    }

    public Locator getNestedDetailPanel() {
        return nestedDetailPanel;
    }

    // SplitterMaxMin getters
    public Locator getMasterPanelWithConstraints() {
        return masterPanelWithConstraints;
    }

    public Locator getDetailPanelWithConstraints() {
        return detailPanelWithConstraints;
    }

    // SplitterPosition getters
    public Locator getPositionedMasterPanel() {
        return positionedMasterPanel;
    }

    public Locator getPositionedDetailPanel() {
        return positionedDetailPanel;
    }
} 