package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableEditDataIT extends BaseTest {

    @BeforeEach
    public void setupTableEditData() {
        page.navigate("https://docs.webforj.com/tableeditdata?");
    }

    @BrowserTest
    public void testEditButton() {
        Locator editButton = page.locator("dwc-icon-button[name='pencil-pin'] >> button").first();
        editButton.click();

        Locator input = page.locator("#field-1");
        input.clear();
        input.fill("Somebody I Used To Know");
        Locator saveButton = page.locator("dwc-button[dwc-id='16']");
        saveButton.click();

        Locator title = page.locator("tr td").nth(1).first();
        assertThat(title).hasText("Somebody I Used To Know");
    }
} 