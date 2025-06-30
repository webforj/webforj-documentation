package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableBasicViewIT extends BaseTest {

    @BeforeEach
    public void setupTableBasic() {
        page.navigate("https://docs.webforj.com/tablebasic?");
    }

    @BrowserTest
    public void testVerifyPage() {
        List<String> expectedHeaders = Arrays.asList("Number", "Title", "Artist", "Genre", "Cost");

        for (String header : expectedHeaders) {
            Locator columnHeader = page.locator("th[data-column='" + header + "']");
            assertThat(columnHeader).hasText(header);
        }
    }
} 