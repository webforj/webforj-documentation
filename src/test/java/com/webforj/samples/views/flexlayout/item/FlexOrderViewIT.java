package com.webforj.samples.views.flexlayout.item;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.flexlayout.item.FlexOrderPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FlexOrderViewIT extends BaseTest {

  private FlexOrderPage flexOrderPage;

  @BeforeEach
  public void setupFlexOrder() {
    navigateToRoute(FlexOrderPage.getRoute());
    flexOrderPage = new FlexOrderPage(page);
  }

  @Test
  public void testZeroOrderPositionsBoxAtStart() {
    flexOrderPage.getSetOrderButton().click();
    assertThat(flexOrderPage.buttonValue(0)).isVisible();
  }
}
