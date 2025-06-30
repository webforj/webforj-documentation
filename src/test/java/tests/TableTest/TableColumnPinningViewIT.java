package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableColumnPinningViewIT extends BaseTest {

    @BeforeEach
    public void setupTableColumnPinning() {
        page.navigate("https://docs.webforj.com/tablecolumnpinning?");
    }

    @BrowserTest
    public void testEditButton() {
        Locator editButtonPosition = page.locator("td[part~='cell-pinned-right']").first();
        Locator editButton = page.locator("td dwc-button").first();

        assertThat(editButtonPosition).hasAttribute("style", Pattern.compile(".*sticky; right: 0px;.*"));

        editButton.click();
        Locator dialogBox = page.locator("dwc-dialog[type='msgbox'] >> section");
        assertThat(dialogBox).hasText("You asked to edit record number 000001.");
    }
} 