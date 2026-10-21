package com.webforj.samples.views.button;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.list.ChoiceBox;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class ButtonExpansesViewTest {
  @ParameterizedTest
  @EnumSource(value = Expanse.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
  void shouldResizeTheButtonToThePickedExpanse(Expanse expanse, WebforjTestbed context) {
    context.add(new ButtonExpansesView());

    var choices = ComponentLocator.of(ChoiceBox.class).locate();
    var button = ComponentLocator.of(Button.class).locate();

    UserInteraction.selectKey(choices, expanse);

    assertEquals(expanse, button.getExpanse());
    assertEquals(expanse.name(), button.getText());
  }
}
