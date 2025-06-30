package tests.DialogTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.BoundingBox;

import pages.DialogPage.DialogDraggablePage;
import tests.BaseTest;
import utils.WaitUtil;
import utils.annotations.BrowserTest;

public class DialogDraggingIT extends BaseTest {

    private DialogDraggablePage dialogDraggablePage;

    @BeforeEach
    public void setupDialogDragging() {
        navigateToRoute(DialogDraggablePage.getRoute());
        dialogDraggablePage = new DialogDraggablePage(page);
    }

    @BrowserTest
    public void testSnappingX() {
        BoundingBox box = dialogDraggablePage.getDialogHeader().boundingBox();

        page.mouse().move(box.x + box.width / 2, box.y + box.height / 2);
        page.mouse().down();
        page.mouse().move(0, box.y + box.height / 2);
        page.mouse().up();

        assertThat(dialogDraggablePage.getDialog()).hasAttribute("style", Pattern.compile(".*--_dwc-dialog-posy: auto;.*"));
    }

    @BrowserTest
    public void testSnappingY() {
        BoundingBox box = dialogDraggablePage.getDialogHeader().boundingBox();

        page.mouse().move(box.x + box.width / 2, box.y + box.height / 2);
        page.mouse().down();
        page.mouse().move(box.x + box.width / 2, 0);
        page.mouse().up();

        assertThat(dialogDraggablePage.getDialog()).hasAttribute("style", Pattern.compile(".*--_dwc-dialog-posx: auto;.*"));
    }
}