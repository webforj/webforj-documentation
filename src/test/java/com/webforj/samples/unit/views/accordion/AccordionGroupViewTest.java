package com.webforj.samples.unit.views.accordion;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.samples.views.accordion.AccordionGroupView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class AccordionGroupViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AccordionGroupView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldCloseTheOpenPanelWhenTheUserOpensAnother(WebforjTestbed context) {
    context.add(new AccordionGroupView());

    var first = ComponentLocator.of(AccordionPanel.class).byText("What is webforJ?").locate();
    var second =
        ComponentLocator.of(AccordionPanel.class).byText("How do grouped panels work?").locate();
    var third =
        ComponentLocator.of(AccordionPanel.class).byText("Can I have multiple groups?").locate();

    UserInteraction.open(second);

    assertTrue(second.isOpened());
    assertFalse(first.isOpened());
    assertFalse(third.isOpened());
  }
}
