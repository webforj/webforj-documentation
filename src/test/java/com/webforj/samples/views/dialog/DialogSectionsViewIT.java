package com.webforj.samples.views.dialog;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webforj.samples.pages.dialog.DialogSectionsPage;
import com.webforj.samples.views.BaseTest;

public class DialogSectionsViewIT extends BaseTest {

    private DialogSectionsPage dialogSectionsPage;

    @BeforeEach
    public void setupDialogSections() {
        navigateToRoute(DialogSectionsPage.getRoute());
        dialogSectionsPage = new DialogSectionsPage(page);
    }

    @Test
    public void testSectionsAreVisible() {
        assertThat(dialogSectionsPage.getHeader()).isVisible();
        assertThat(dialogSectionsPage.getContent()).isVisible();
        assertThat(dialogSectionsPage.getFooter()).isVisible();
    }
}
