package com.webforj.samples.views.accordion;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class AccordionNestedViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AccordionNestedView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldOpenAnInnerPanelInsideTheOpenOuterPanel(WebforjTestbed context) {
    context.add(new AccordionNestedView());

    var outer =
        ComponentLocator.of(AccordionPanel.class)
            .byText("Outer Panel (contains nested accordion)")
            .locate();
    var innerA =
        ComponentLocator.of(AccordionPanel.class).within(outer).byText("Inner Panel A").locate();

    UserInteraction.open(innerA);

    assertTrue(innerA.isOpened());
    assertTrue(outer.isOpened());
  }

  @Test
  void shouldCloseTheOtherInnerPanelWithoutClosingTheOuterPanel(WebforjTestbed context) {
    context.add(new AccordionNestedView());

    var outer =
        ComponentLocator.of(AccordionPanel.class)
            .byText("Outer Panel (contains nested accordion)")
            .locate();
    var innerA =
        ComponentLocator.of(AccordionPanel.class).within(outer).byText("Inner Panel A").locate();
    var innerB =
        ComponentLocator.of(AccordionPanel.class).within(outer).byText("Inner Panel B").locate();
    UserInteraction.open(innerA);

    UserInteraction.open(innerB);

    assertTrue(innerB.isOpened());
    assertFalse(innerA.isOpened());
    assertTrue(outer.isOpened());
  }
}
