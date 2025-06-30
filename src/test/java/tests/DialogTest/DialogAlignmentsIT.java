package tests.DialogTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.DialogPage.DialogAlignmentsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DialogAlignmentsIT extends BaseTest {

    private DialogAlignmentsPage dialogAlignmentsPage;

    @BeforeEach
    public void setDialogAlignments() {
        navigateToRoute(DialogAlignmentsPage.getRoute());
        dialogAlignmentsPage = new DialogAlignmentsPage(page);
    }

    @BrowserTest
    public void testVerticalPositions() {
        dialogAlignmentsPage.getSelectAlignmentButton().click();
        dialogAlignmentsPage.getSelectTop().click();
        assertThat(dialogAlignmentsPage.getDialog()).hasAttribute("alignment", "top");

        dialogAlignmentsPage.getSelectAlignmentButton().click();
        dialogAlignmentsPage.getSelectCenter().click();
        assertThat(dialogAlignmentsPage.getDialog()).hasAttribute("alignment", "center");

        dialogAlignmentsPage.getSelectAlignmentButton().click();
        dialogAlignmentsPage.getSelectBottom().click();
        assertThat(dialogAlignmentsPage.getDialog()).hasAttribute("alignment", "bottom");
    }
} 