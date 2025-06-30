package tests.ColumnsLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.ColumnsLayoutPage.ColumnsLayoutSpanPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ColumnsLayoutSpansTestsIT extends BaseTest {

    private ColumnsLayoutSpanPage columnsLayout;

    @BeforeEach
    public void setupColumnsLayoutSpans() {
        navigateToRoute(ColumnsLayoutSpanPage.getRoute());
        columnsLayout = new ColumnsLayoutSpanPage(page);
    }

    @BrowserTest
    public void testMediumBreakpointWithSpan2() {
        page.setViewportSize(768, 844);

        assertThat(columnsLayout.getEmail()).hasAttribute("style",
                Pattern.compile("(grid-column: span 2;|grid-column-start: span 2;)"));
    }
} 