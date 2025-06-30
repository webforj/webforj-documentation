package tests.ColumnsLayoutTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import pages.ColumnsLayoutPage.ColumnsLayoutFormPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class ColumnsLayoutFormTestsIT extends BaseTest {

    private ColumnsLayoutFormPage columnsLayout;

    @BeforeEach
    public void setupColumnsLayoutForm() {
        navigateToRoute(ColumnsLayoutFormPage.getRoute());
        columnsLayout = new ColumnsLayoutFormPage(page);
    }

    @BrowserTest
    public void testVerifyAllComponentsArePresent() {
        List<Locator> elements = Arrays.asList(
                columnsLayout.getFirstName(),
                columnsLayout.getLastName(),
                columnsLayout.getEmail(),
                columnsLayout.getPassword(),
                columnsLayout.getConfirmPassword(),
                columnsLayout.getStateDropdown(),
                columnsLayout.getZipCode(),
                columnsLayout.getCancelButton(),
                columnsLayout.getSubmitButtonLayoutForm());

        for (Locator element : elements) {
            assertThat(element).isVisible();
        }
    }
} 