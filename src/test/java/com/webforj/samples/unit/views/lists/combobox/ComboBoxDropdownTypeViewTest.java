package com.webforj.samples.unit.views.lists.combobox;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ComboBox;
import com.webforj.samples.views.lists.combobox.ComboBoxDropdownTypeView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ComboBoxDropdownTypeViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ComboBoxDropdownTypeView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldUseTheDemoDropdownType(WebforjTestbed context) {
    context.add(new ComboBoxDropdownTypeView());

    var list = ComponentLocator.of(ComboBox.class).locate();

    assertEquals("demo-dropdown-type", list.getDropdownType());
  }
}
