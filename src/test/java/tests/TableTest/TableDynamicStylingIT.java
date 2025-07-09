package tests.TableTest;

import org.junit.jupiter.api.BeforeEach;

import pages.TablePages.TableDynamicStylingViewPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableDynamicStylingIT extends BaseTest {

    private TableDynamicStylingViewPage tablePage;

    @BeforeEach
    public void setup() {
        navigateToRoute(TableDynamicStylingViewPage.getRoute());
        tablePage = new TableDynamicStylingViewPage(page);
    }

    @BrowserTest
    public void testBasicFunctionality() {
        // Add test implementation later
    }
}