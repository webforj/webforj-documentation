package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.options.AriaRole;

import tests.BaseTest;
import utils.annotations.BrowserTest;
import pages.TablePages.TableSingleSelectionViewPage;

public class TableSingleSelectionIT extends BaseTest {

    @BeforeEach
    public void setupTableSingleSelection() {
        navigateToRoute(TableSingleSelectionViewPage.getRoute());
    }

    @BrowserTest
    public void testSingleItemSelectionAndConfirmationDialog() {
        page.getByText("Mississippi Blues").click();
        assertThat(page.getByRole(AriaRole.BANNER)).containsText("Record Number 000001");
        assertThat(page.locator("section"))
                .hasText("You have selected Mississippi Blues by John Hurt & The Ramblers");
    }
}