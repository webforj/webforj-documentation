package com.webforj.samples.views.dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class DialogPositioningViewTest {
  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      value = {
        "Upper left  | 5%                | 10%",
        "Lower right | calc(95% - 20rem) | calc(90% - 8rem)"
      })
  void shouldMoveTheDialogToTheCssPositionOfThePickedPreset(
      String preset, String posx, String posy, WebforjTestbed context) {
    context.add(new DialogPositioningView());

    var dialog = ComponentLocator.of(Dialog.class).locate();

    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText(preset).locate());

    assertEquals(posx, dialog.getPosx());
    assertEquals(posy, dialog.getPosy());
  }

  @Test
  void shouldMoveTheDialogBackToTheMiddle(WebforjTestbed context) {
    context.add(new DialogPositioningView());

    var dialog = ComponentLocator.of(Dialog.class).locate();

    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText("Upper left").locate());
    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText("Middle").locate());

    assertEquals("calc(50% - 10rem)", dialog.getPosx());
    assertEquals("calc(50% - 4rem)", dialog.getPosy());
  }
}
