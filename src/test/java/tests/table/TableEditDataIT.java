package tests.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.table.TableEditDataPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableEditDataIT extends BaseTest {

    private TableEditDataPage tablePage;

    @BeforeEach
    public void setupTableEditData() {
        navigateToRoute(TableEditDataPage.getRoute());
        tablePage = new TableEditDataPage(page);
    }

    @BrowserTest
    public void testEditButton() {
        tablePage.getEditButton().click();

        tablePage.getInput().clear();
        tablePage.getInput().fill("Somebody I Used To Know");
        tablePage.getSaveButton().click();

        assertThat(tablePage.getTitle()).hasText("Somebody I Used To Know");
    }
}