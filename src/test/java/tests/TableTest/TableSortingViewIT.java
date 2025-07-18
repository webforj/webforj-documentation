package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import tests.BaseTest;
import utils.annotations.BrowserTest;
import pages.TablePages.TableSortingViewPage;

public class TableSortingViewIT extends BaseTest {

    private TableSortingViewPage tableSorting;

    @BeforeEach
    public void setupTableSorting() {
        navigateToRoute(TableSortingViewPage.getRoute());
        tableSorting = new TableSortingViewPage(page);
    }

    @BrowserTest
    public void testSortAscendingAndDescendingOrder() {
        String defaultTitle = "Mississippi Blues";
        String ascTitle = "Abbey Road";
        String descTitle = "War";

        assertThat(tableSorting.getFirstTitleCell()).hasText(defaultTitle);

        tableSorting.getTitleSorting().click();
        assertThat(tableSorting.getFirstTitleCell()).hasText(ascTitle);

        tableSorting.getTitleSorting().click();
        assertThat(tableSorting.getFirstTitleCell()).hasText(descTitle);

        tableSorting.getTitleSorting().click();
        assertThat(tableSorting.getFirstTitleCell()).hasText(defaultTitle);
    }
}