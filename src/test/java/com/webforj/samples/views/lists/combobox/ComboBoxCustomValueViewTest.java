package com.webforj.samples.views.lists.combobox;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ComboBox;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ComboBoxCustomValueViewTest {
  @Test
  void shouldToggleCustomValues(WebforjTestbed context) {
    context.add(new ComboBoxCustomValueView());

    var combo = ComponentLocator.of(ComboBox.class).locate();
    var toggle = ComponentLocator.of(RadioButton.class).locate();

    assertFalse(combo.isAllowCustomValue());

    UserInteraction.check(toggle);
    assertTrue(combo.isAllowCustomValue());

    UserInteraction.uncheck(toggle);
    assertFalse(combo.isAllowCustomValue());
  }
}
