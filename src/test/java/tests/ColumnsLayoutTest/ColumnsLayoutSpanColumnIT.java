package tests.ColumnsLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.ColumnsLayoutPage.ColumnsLayoutSpanColumnPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ColumnsLayoutSpanColumnIT extends BaseTest {

    private ColumnsLayoutSpanColumnPage columnsLayout;

    @BeforeEach
    public void setupColumnsLayoutSpans() {
        navigateToRoute(ColumnsLayoutSpanColumnPage.getRoute());
        columnsLayout = new ColumnsLayoutSpanColumnPage(page);
    }

    @BrowserTest
    public void testMediumBreakpointWithSpan2() {
        page.setViewportSize(768, 844);

        assertThat(columnsLayout.getEmail()).hasAttribute("style",
                Pattern.compile("(grid-column: span 2;|grid-column-start: span 2;)"));
    }
}