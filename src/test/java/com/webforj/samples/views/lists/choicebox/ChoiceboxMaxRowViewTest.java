package com.webforj.samples.views.lists.choicebox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.NumberField;
import com.webforj.component.list.ChoiceBox;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ChoiceboxMaxRowViewTest {
  @Test
  void shouldLimitTheDropdownToTheAppliedRowCount(WebforjTestbed context) {
    context.add(new ChoiceboxMaxRowView());

    var list = ComponentLocator.of(ChoiceBox.class).locate();
    var count = ComponentLocator.of(NumberField.class).locate();

    UserInteraction.type(count, "7");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Apply").locate());

    assertEquals(7, list.getMaxRowCount());
  }
}
