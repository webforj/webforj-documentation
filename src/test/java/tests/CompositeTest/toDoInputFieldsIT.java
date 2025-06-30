package tests.CompositeTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import pages.CompositePage.CompositeDemoPage;
import tests.BaseTest;
import utils.LoggerUtil;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class toDoInputFieldsIT extends BaseTest {

    private CompositeDemoPage compositePage;

    @BeforeEach
    public void setupToCompositePage() {
        navigateToRoute(CompositeDemoPage.getRoute());
        compositePage = new CompositeDemoPage(page);
        page.waitForLoadState();
    }

    @BrowserTest
    public void testTodosTitle() {
        assertTrue(compositePage.isTodosTitleDisplayed());
    }

    @BrowserTest
    public void testNewItem() {
        compositePage.addTodoItem("Apple");

        assertEquals(compositePage.getTodoItemText(3), "Apple");
    }

    @BrowserTest
    public void testItemMarkCompleted() {
        compositePage.clickToggle();
        assertTrue(compositePage.isTodoItemCompleted());
    }

    @BrowserTest
    public void testToggleOffCompletedItem() {
        compositePage.waitForNoStrikethrough();

        compositePage.clickToggle();
        compositePage.waitForStrikethrough();
        assertTrue(compositePage.hasStrikethroughStyle());

        compositePage.clickToggle();
        compositePage.waitForNoStrikethrough();
        assertTrue(compositePage.hasNoStrikethroughStyle());
    }

    @Disabled
    @BrowserTest
    public void testInvalidItem() {
        LoggerUtil.info("Bug report #207 skipped.");
        compositePage.addTodoItem(" ");
        page.waitForTimeout(1000);
        int totalItemCount = compositePage.getTodoItemsSelector().count();
        assertEquals(totalItemCount, 3);
    }

    @Disabled
    @BrowserTest
    public void testDuplicateItem() {
        LoggerUtil.info("Bug report #207 skipped.");
        compositePage.addTodoItem("Apple");
        assertEquals("Apple", compositePage.getTodoItemText(3));
        compositePage.addTodoItem("Apple");

        WaitUtil.waitForVisible(compositePage.getTodoItemsSelector().nth(4));

        int totalItemCount = compositePage.getTodoItemsSelector().count();
        assertEquals(totalItemCount, 4);
    }
} 