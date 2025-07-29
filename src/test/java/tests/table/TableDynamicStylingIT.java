package tests.table;

import org.junit.jupiter.api.BeforeEach;

import pages.table.TableDynamicStylingPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableDynamicStylingIT extends BaseTest {

    private TableDynamicStylingPage tablePage;

    @BeforeEach
    public void setup() {
        navigateToRoute(TableDynamicStylingPage.getRoute());
        tablePage = new TableDynamicStylingPage(page);
    }

    @BrowserTest
    public void testBasicFunctionality() {
        // Add test implementation later
    }
}