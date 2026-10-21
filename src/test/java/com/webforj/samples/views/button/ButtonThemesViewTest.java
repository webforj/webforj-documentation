package com.webforj.samples.views.button;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ButtonThemesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ButtonThemesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheSevenFilledThemesAndThenTheSevenOutlinedThemes(WebforjTestbed context) {
    context.add(new ButtonThemesView());

    var buttons = ComponentLocator.of(Button.class).all();

    assertEquals(
        List.of(
            ButtonTheme.DANGER,
            ButtonTheme.DEFAULT,
            ButtonTheme.GRAY,
            ButtonTheme.INFO,
            ButtonTheme.PRIMARY,
            ButtonTheme.SUCCESS,
            ButtonTheme.WARNING,
            ButtonTheme.OUTLINED_DANGER,
            ButtonTheme.OUTLINED_DEFAULT,
            ButtonTheme.OUTLINED_GRAY,
            ButtonTheme.OUTLINED_INFO,
            ButtonTheme.OUTLINED_PRIMARY,
            ButtonTheme.OUTLINED_SUCCESS,
            ButtonTheme.OUTLINED_WARNING),
        buttons.stream().map(Button::getTheme).toList());
  }
}
