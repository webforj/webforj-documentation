package com.webforj.samples.views.table;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.table.TableColumnPinningPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TableColumnPinningViewIT extends BaseTest {

  private TableColumnPinningPage tablePage;

  @BeforeEach
  public void setupTableColumnPinning() {
    navigateToRoute(TableColumnPinningPage.getRoute());
    tablePage = new TableColumnPinningPage(page);
  }

  @Test
  public void testEditButtonTableColumnPinning() {
    tablePage.getEditButton().click();
    assertThat(tablePage.getDialogBox()).hasText("You asked to edit record number 000001.");
  }
}
