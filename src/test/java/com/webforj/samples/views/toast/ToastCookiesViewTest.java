package com.webforj.samples.views.toast;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.toast.Toast;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@WebforjTest
class ToastCookiesViewTest {
  @ParameterizedTest
  @ValueSource(strings = {"Accept all cookies", "Necessary cookies only"})
  void shouldCloseThePromptWhenTheUserAnswersThroughItsButtons(
      String answer, WebforjTestbed context) {
    context.getFrame();
    context.add(new ToastCookiesView());

    var prompt = ComponentLocator.of(Toast.class).locate();

    UserInteraction.click(ComponentLocator.of(Button.class).within(prompt).byText(answer).locate());

    assertTrue(prompt.isDestroyed());
    assertNotSame(prompt, ComponentLocator.of(Toast.class).locate());
  }
}
