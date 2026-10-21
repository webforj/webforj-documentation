package com.webforj.samples.views.accordion;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.icons.Icon;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class AccordionCustomIconViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AccordionCustomIconView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowThePlusIconInPlaceOfTheChevron(WebforjTestbed context) {
    context.add(new AccordionCustomIconView());

    var panel = ComponentLocator.of(AccordionPanel.class).byText("Plus Icon Panel").locate();
    var icon = assertInstanceOf(Icon.class, panel.getIcon());

    assertEquals("plus", icon.getName());
  }
}
