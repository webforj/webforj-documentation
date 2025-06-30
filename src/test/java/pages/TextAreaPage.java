package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TextAreaPage extends BasePage {

    // Feedback TextArea Elements
    private final Locator mainLabel;
    private final Locator feedbackArea;
    private final Locator charactersCount;
    private final Locator submitButton;
    private final Locator donationToaster;

    // Predicted Text Demo Elements
    private final Locator predictedTextArea;

    // TextArea States Elements
    private final Locator readOnlyArea;
    private final Locator disabledArea;

    // TextArea Validation Elements
    private final Locator validationTextArea;
    private final Locator maxLengthField;
    private final Locator maxLengthInput;
    private final Locator maxLinesInput;
    private final Locator maxParagraphLengthInput;

    public TextAreaPage(Page page) {
        super(page);

        // Feedback TextArea (elements from FeedbackTextAreaIT)
        mainLabel = page.locator("dwc-textarea[dwc-id='12'] >> label");
        feedbackArea = page.locator("dwc-textarea[dwc-id='12'] >> textarea");
        charactersCount = page.locator("div[dwc-id='13'] > p[dwc-id='14']");
        submitButton = page.locator("div[dwc-id='13'] > dwc-button[dwc-id='15']");
        donationToaster = page.locator("dwc-toast-group");

        // Predicted Text Demo (elements from PredictedTextDemoIT)
        predictedTextArea = page.locator("#cedit-1");

        // TextArea States (elements from TextAreaStatesIT)
        readOnlyArea = page.locator("dwc-textarea[dwc-id='12']");
        disabledArea = page.locator("dwc-textarea[dwc-id='14'] >> textarea");

        // TextArea Validation (elements from TextAreaValidationIT)
        validationTextArea = page.locator("dwc-textarea[dwc-id='12'] >> textarea");
        maxLengthField = page.locator("dwc-field[dwc-id='14']");
        maxLengthInput = page.locator("dwc-field[dwc-id='14'] >> input");
        maxLinesInput = page.locator("dwc-field[dwc-id='16'] >> input");
        maxParagraphLengthInput = page.locator("dwc-field[dwc-id='18'] >> input");
    }

    // Feedback TextArea Getters
    public Locator getMainLabel() {
        return mainLabel;
    }

    public Locator getFeedbackArea() {
        return feedbackArea;
    }

    public Locator getCharactersCount() {
        return charactersCount;
    }

    public Locator getSubmitButton() {
        return submitButton;
    }

    public Locator getDonationToaster() {
        return donationToaster;
    }

    // Predicted Text Demo Getters
    public Locator getPredictedTextArea() {
        return predictedTextArea;
    }

    // TextArea States Getters
    public Locator getReadOnlyArea() {
        return readOnlyArea;
    }

    public Locator getDisabledArea() {
        return disabledArea;
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