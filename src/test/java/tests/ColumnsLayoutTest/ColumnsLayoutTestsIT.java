package tests.ColumnsLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.ColumnsLayoutPage.ColumnsLayoutDemoPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ColumnsLayoutTestsIT extends BaseTest {

    private ColumnsLayoutDemoPage columnsLayout;

    @BeforeEach
    public void navigateColumnsLayout() {
        navigateToRoute(ColumnsLayoutDemoPage.getRoute());
        columnsLayout = new ColumnsLayoutDemoPage(page);
    }

    @BrowserTest
    public void testTwoColumnsDefault() {
        assertThat(columnsLayout.getDwcColumnsLayout()).hasAttribute("data-columns", "2");
    }

    @BrowserTest
    public void testSetSpanPropertyFunction() {

        assertThat(columnsLayout.getEmail()).hasAttribute("style",
                Pattern.compile("(grid-column: span 2;|grid-column-start: span 2;)"));
        assertThat(columnsLayout.getSubmitButton()).hasAttribute("style",
                Pattern.compile("(grid-column: span 2;|grid-column-start: span 2;)"));
    }
} 