package tests.ColumnsLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import pages.ColumnsLayoutPage.ColumnsLayoutAlignmentPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ColumnsLayoutAlignmentTestsIT extends BaseTest {

    private ColumnsLayoutAlignmentPage columnsLayout;

    @BeforeEach
    public void setupColumnsLayoutAlignment() {
        navigateToRoute(ColumnsLayoutAlignmentPage.getRoute());
        columnsLayout = new ColumnsLayoutAlignmentPage(page);
    }

    @BrowserTest
    public void testEndAlignment() {
         assertThat(columnsLayout.getSubmitButton()).hasAttribute("style", Pattern.compile("justify-self: end;"));
    }
}