package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableSortingIT extends BaseTest {

    @BeforeEach
    public void setupTableSorting() {
        page.navigate("https://docs.webforj.com/tablesorting?");
    }

    @BrowserTest
    public void testSortAscendingAndDescendingOrder() {
        String defaultTitle = "Mississippi Blues";
        String ascTitle = "Abbey Road";
        String descTitle = "War";

        Locator titleSorting = page.locator("dwc-table >> text=Title");

        Locator firstTitleCell = page.locator("tr[part*='row'] td[part*='cell'] div[part='cell-label']")
                .first();

        assertThat(firstTitleCell).hasText(defaultTitle);

        titleSorting.click();
        assertThat(firstTitleCell).hasText(ascTitle);

        titleSorting.click();
        assertThat(firstTitleCell).hasText(descTitle);

        titleSorting.click();
        assertThat(firstTitleCell).hasText(defaultTitle);
    }
} 