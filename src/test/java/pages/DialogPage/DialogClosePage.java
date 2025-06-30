    package pages.DialogPage;

    import com.microsoft.playwright.Locator;
    import com.microsoft.playwright.Page;
    import pages.BasePage;

    public class DialogClosePage extends BasePage {

        private static final String ROUTE = "dialogcloseview";

        private final Locator dialogHeader;
        private final Locator closeDialog;
        private final Locator showDialog;
        private final Locator dialogPart;

        public DialogClosePage(Page page) {
            super(page);
            
        dialogHeader = page.locator("div[dwc-id='12']");
        closeDialog = page.locator("dwc-button[dwc-id='13']");
        showDialog = page.locator("dwc-button[dwc-id='14'] >> button");
        dialogPart = page.locator("div[part='dialog']");

        }

        public static String getRoute() {
            return ROUTE;
        }

        public Locator getDialogHeader() {
            return dialogHeader;
        }

        public Locator getCloseDialog() {
            return closeDialog;
        }

        public Locator getShowDialog() {
            return showDialog;
        }

        public Locator getDialogPart() {
            return dialogPart;
        }
} 