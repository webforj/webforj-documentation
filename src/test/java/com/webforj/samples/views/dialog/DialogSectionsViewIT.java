package com.webforj.samples.views.dialog;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.SupportedLanguage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webforj.samples.pages.dialog.DialogSectionsPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class DialogSectionsViewIT extends BaseTest {

  private DialogSectionsPage dialogSectionsPage;

  public void setupDialogSections(SupportedLanguage language) {
    navigateToRoute(DialogSectionsPage.getRoute(language));
    dialogSectionsPage = new DialogSectionsPage(page);
  }


  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testSectionsAreVisible(SupportedLanguage language) {
    setupDialogSections(language);
    assertThat(dialogSectionsPage.getHeader()).isVisible();
    assertThat(dialogSectionsPage.getContent()).isVisible();
    assertThat(dialogSectionsPage.getFooter()).isVisible();
  }
}
