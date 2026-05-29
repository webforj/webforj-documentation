package com.webforj.samples.views.element;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.webforj.samples.pages.element.ElementInputEventPage;
import com.webforj.samples.views.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ElementInputEventViewIT extends BaseTest {

  private ElementInputEventPage elementInputEventPage;

  @BeforeEach
  public void setupInputEvent() {
    navigateToRoute(ElementInputEventPage.getRoute());
    elementInputEventPage = new ElementInputEventPage(page);
  }

  @Test
  public void testDialogMessageIsShownWhenInputFieldIsFilled() {
    elementInputEventPage.getInputField().fill("Hello World");
    elementInputEventPage.getInputField().press("Enter");

    assertThat(elementInputEventPage.getDialogMessage()).hasText("Hello World");

    elementInputEventPage.getOKButton().click();
  }
}
