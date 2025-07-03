package pages.TextAreaPage;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.webforj.samples.config.RouteConfig;
import pages.BasePage;

public class TextAreaValidationPage extends BasePage {

    private static final String ROUTE = RouteConfig.TEXT_AREA_VALIDATION;

    // TextArea Validation Elements
    private final Locator validationTextArea;
    private final Locator maxLengthField;
    private final Locator maxLengthInput;
    private final Locator maxLinesInput;
    private final Locator maxParagraphLengthInput;

    public TextAreaValidationPage(Page page) {
        super(page);

        // TextArea Validation (elements from TextAreaValidationIT)
        validationTextArea = page.locator("dwc-textarea[dwc-id='12'] >> textarea");
        maxLengthField = page.locator("dwc-field[dwc-id='14']");
        maxLengthInput = page.locator("dwc-field[dwc-id='14'] >> input");
        maxLinesInput = page.locator("dwc-field[dwc-id='16'] >> input");
        maxParagraphLengthInput = page.locator("dwc-field[dwc-id='18'] >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

    // TextArea Validation Getters
    public Locator getValidationTextArea() {
        return validationTextArea;
    }

    public Locator getMaxLengthField() {
        return maxLengthField;
    }

    public Locator getMaxLengthInput() {
        return maxLengthInput;
    }

    public Locator getMaxLinesInput() {
        return maxLinesInput;
    }

    public Locator getMaxParagraphLengthInput() {
        return maxParagraphLengthInput;
    }
}