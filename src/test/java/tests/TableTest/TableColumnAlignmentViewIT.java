package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableColumnAlignmentViewIT extends BaseTest {

    @BeforeEach
    public void setupTableColumnAlignment() {
        page.navigate("https://docs.webforj.com/tablecolumnalignment?");
    }

    @BrowserTest
    public void testColumnSetToTheRight() {
        Locator costColumn = page.locator("th[part*='cell cell-header cell-align-right']");

        assertThat(costColumn).isVisible();
    }
} 