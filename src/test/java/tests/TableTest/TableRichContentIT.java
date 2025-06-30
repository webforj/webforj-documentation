package tests.TableTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;

import tests.BaseTest;
import utils.annotations.BrowserTest;

public class TableRichContentIT extends BaseTest {

    @BeforeEach
    public void setupTableRichContent() {
        page.navigate("https://docs.webforj.com/tablerichcontent?");
    }

    @BrowserTest
    public void testMasterCheckbox() {
        Locator masterCheckBox = page.locator("thead input[type='checkbox']");

        masterCheckBox.click();

        List<Locator> checkboxes = page.locator("tbody div[part='cell-content-checkbox']").all();

        for (Locator checkbox : checkboxes) {
            assertThat(checkbox).isChecked();
        }
        masterCheckBox.click();

        for (Locator checkbox : checkboxes) {
            assertThat(checkbox).not().isChecked();
        }
    }

    @BrowserTest
    public void testIndividualCheckbox() {
        Locator firstCheckbox = page.locator("dwc-checkbox").first();
        Locator checkboxInput = firstCheckbox.locator("input[type='checkbox']");
        checkboxInput.click();

        assertThat(checkboxInput).isChecked();
    }

    @BrowserTest
    public void testTableImages() {
        Locator images = page.locator("img[part='avatar-img']");

        for (int i = 0; i < images.count(); i++) {
            Locator image = images.nth(i);

            boolean isBroken = (boolean) image.evaluate(
                    "img => !img.loaded || img.width < 32 || img.height < 32");
            assertFalse(isBroken);
        }
    }
} 