package pages.textarea;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pages.BasePage;

public class TextAreaValidationPage extends BasePage {

    private static final String ROUTE = "textareavalidation";

    // TextArea Validation Elements
    private final Locator validationTextArea;
    private final Locator maxLengthField;
    private final Locator maxLengthInput;
    private final Locator maxLinesInput;
    private final Locator maxParagraphLengthInput;

    public TextAreaValidationPage(Page page) {
        super(page);

        // TextArea Validation (elements from TextAreaValidationIT)
        validationTextArea = page.locator("dwc-textarea:has-text('Validation Playground') >> textarea");
        maxLengthField = page.locator("dwc-field:has-text('Max Length')");
        maxLengthInput = page.locator("dwc-field:has-text('Max Length') >> input");
        maxLinesInput = page.locator("dwc-field:has-text('Max Lines') >> input");
        maxParagraphLengthInput = page.locator("dwc-field:has-text('Max Paragraph Length') >> input");
    }

    public static String getRoute() {
        return ROUTE;
    }

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