package tests.TextAreaTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TextAreaPage.TextAreaStatesPage;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TextAreaStatesIT extends BaseTest {

    private TextAreaStatesPage textAreaStatesPage;

    @BeforeEach
    public void setupTextAreaStates() {
        navigateToRoute(TextAreaStatesPage.getRoute());
        textAreaStatesPage = new TextAreaStatesPage(page);
    }

    @BrowserTest
    public void testTextAreaStates() {
        assertThat(textAreaStatesPage.getReadOnlyArea()).isVisible();
        assertThat(textAreaStatesPage.getDisabledArea()).isVisible();

        textAreaStatesPage.getReadOnlyArea().click();
        assertThat(textAreaStatesPage.getReadOnlyArea()).hasAttribute("has-focus", "");

        assertThat(textAreaStatesPage.getDisabledArea()).isDisabled();

    }
}