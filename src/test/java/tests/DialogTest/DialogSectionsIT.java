package tests.DialogTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

import pages.DialogPage.DialogSectionsPage;
import tests.BaseTest;
import utils.annotations.BrowserTest;

public class DialogSectionsIT extends BaseTest {

    private DialogSectionsPage dialogSectionsPage;

    @BeforeEach
    public void setupDialogSections() {
        navigateToRoute(DialogSectionsPage.getRoute());
        dialogSectionsPage = new DialogSectionsPage(page);
    }

    @BrowserTest
    public void testDialog() {
        assertThat(dialogSectionsPage.getHeader()).hasText("Header");
        assertThat(dialogSectionsPage.getContent()).hasText("Content");
        assertThat(dialogSectionsPage.getFooter()).hasText("Footer");
    }
} 