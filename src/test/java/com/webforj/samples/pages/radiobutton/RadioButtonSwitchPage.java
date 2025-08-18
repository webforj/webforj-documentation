package com.webforj.samples.pages.radiobutton;

import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Page;

import com.webforj.samples.pages.BasePage;

public class RadioButtonSwitchPage extends BasePage {

  private static final String ROUTE = "radiobuttonswitch";

  private final Locator switchHost;
  private final Locator switchInput;
  private final Locator switchControl;

  public RadioButtonSwitchPage(Page page) {
    super(page);

    switchHost = page.locator("dwc-radio")
        .filter(new Locator.FilterOptions()
            .setHas(page.locator("label[part='label']:has-text('Switch')")));

    switchInput = switchHost.locator("input[part='input']:visible").first();
    switchControl = switchHost.locator("[part~='control']").first();
  }

  public static String getRoute() {
    return ROUTE;
  }

  public void waitReady() {
    assertThat(page.locator("dwc-radio input[part='input']:visible"))
        .hasCount(2);
  }

  public Locator getSwitchRadio() {
    return switchInput;
  }

  public Locator getSwitchControl() {
    return switchControl;
  }
}