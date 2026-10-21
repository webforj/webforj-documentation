package com.webforj.samples.views.lists.choicebox;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.list.ChoiceBox;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ChoiceboxDropdownTypeViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new ChoiceboxDropdownTypeView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldUseTheDemoDropdownType(WebforjTestbed context) {
    context.add(new ChoiceboxDropdownTypeView());

    var list = ComponentLocator.of(ChoiceBox.class).locate();

    assertEquals("demo-dropdown-type", list.getDropdownType());
  }
}
