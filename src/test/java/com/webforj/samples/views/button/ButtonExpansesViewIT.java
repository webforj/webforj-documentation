package com.webforj.samples.views.button;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.webforj.component.Expanse;
import com.webforj.samples.pages.button.ButtonExpansesPage;
import com.webforj.samples.utils.components.ButtonComponent;
import com.webforj.samples.utils.SupportedLanguage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.regex.Pattern;

public class ButtonExpansesViewIT extends BaseTest {
  private ButtonExpansesPage buttonPage;

  @BeforeEach
  public void setupButtonExpanses() {
    buttonPage = new ButtonExpansesPage(page);
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testChoiceBoxIsVisible(SupportedLanguage language) {
    buttonPage.setRoute(language);
    buttonPage.getChoiceBox().assertIsVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testButtonIsVisible(SupportedLanguage language) {
    buttonPage.setRoute(language);
    buttonPage.getButton("None").assertIsVisible();
  }

  @ParameterizedTest
  @MethodSource("provideRoutes")
  public void testButtonExpanseChangesWhenSelected(SupportedLanguage language) {
    buttonPage.setRoute(language);

    page.getByLabel("chevron down").locator("svg").click();
    page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("XSMALL")).locator("span").click();
    buttonPage.getButton("XSMALL")
      .getLocator().last().assertThat().isVisible();
    // Select LARGE from the ChoiceBox
//    buttonPage.selectExpanse("XSMALL");

    // Verify button text changed to XSMALL
//    button.assertText("XSMALL");

    // Verify button expanse attribute changed to large
//    button.assertExpanse(Expanse.XSMALL);
  }

}
