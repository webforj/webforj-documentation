package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.TablePages.TableColumnPinningViewPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableColumnPinningViewIT extends BaseTest {

    private TableColumnPinningViewPage tablePage;

    @BeforeEach
    public void setupTableColumnPinning() {
        navigateToRoute(TableColumnPinningViewPage.getRoute());
        tablePage = new TableColumnPinningViewPage(page);
    }

    @BrowserTest
    public void testEditButton() {

        assertThat(tablePage.getEditButtonPosition()).hasAttribute("style", Pattern.compile(".*sticky; right: 0px;.*"));

        tablePage.getEditButton().click();
        assertThat(tablePage.getDialogBox()).hasText("You asked to edit record number 000001.");
    }
}