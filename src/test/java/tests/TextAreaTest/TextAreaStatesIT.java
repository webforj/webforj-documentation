package tests.TextAreaTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.TextAreaPage;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TextAreaStatesIT extends BaseTest {

    private TextAreaPage textAreaPage;

    @BeforeEach
    public void setupTextAreaStates() {
        page.navigate("https://docs.webforj.com/webforj/textareastates?");
        textAreaPage = new TextAreaPage(page);
    }

    @BrowserTest
    public void testTextAreaStates() {
        assertThat(textAreaPage.getReadOnlyArea()).isVisible();
        assertThat(textAreaPage.getDisabledArea()).isVisible();

        textAreaPage.getReadOnlyArea().click();
        assertThat(textAreaPage.getReadOnlyArea()).hasAttribute("has-focus", "");

        assertThat(textAreaPage.getDisabledArea()).isDisabled();

    }
} 