package com.webforj.samples.unit.views.accordion;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.exception.InteractionRejectedException;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.accordion.Accordion;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.samples.views.accordion.AccordionDisabledView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class AccordionDisabledViewTest {
  @Test
  void shouldNotOpenTheDisabledPanelWhenTheUserClicksItsHeader(WebforjTestbed context) {
    context.add(new AccordionDisabledView());

    var disabled =
        ComponentLocator.of(AccordionPanel.class).byText("This panel is disabled").locate();

    assertThrows(InteractionRejectedException.class, () -> UserInteraction.open(disabled));
    assertFalse(disabled.isOpened());
  }

  @Test
  void shouldKeepTheDisabledOpenedPanelExpandedWhenTheUserClicksItsHeader(WebforjTestbed context) {
    context.add(new AccordionDisabledView());

    var opened = ComponentLocator.of(AccordionPanel.class).byText("Disabled but opened").locate();

    assertThrows(InteractionRejectedException.class, () -> UserInteraction.close(opened));
    assertTrue(opened.isOpened());
  }

  @Test
  void shouldDisableEveryPanelOfTheGroupWhenTheUserTurnsTheSwitchOff(WebforjTestbed context) {
    context.add(new AccordionDisabledView());

    var accordion = ComponentLocator.of(Accordion.class).locate();
    var toggle = ComponentLocator.of(RadioButton.class).locate();
    var panelTwo =
        ComponentLocator.of(AccordionPanel.class).within(accordion).byText("Panel Two").locate();

    UserInteraction.uncheck(toggle);

    assertTrue(
        ComponentLocator.of(AccordionPanel.class).within(accordion).all().stream()
            .noneMatch(AccordionPanel::isEnabled));
    assertThrows(InteractionRejectedException.class, () -> UserInteraction.open(panelTwo));
  }

  @Test
  void shouldEnableOnlyTheGroupWhenTheUserTurnsTheSwitchBackOn(WebforjTestbed context) {
    context.add(new AccordionDisabledView());

    var accordion = ComponentLocator.of(Accordion.class).locate();
    var toggle = ComponentLocator.of(RadioButton.class).locate();
    var panelTwo =
        ComponentLocator.of(AccordionPanel.class).within(accordion).byText("Panel Two").locate();
    var disabled =
        ComponentLocator.of(AccordionPanel.class).byText("This panel is disabled").locate();
    UserInteraction.uncheck(toggle);

    UserInteraction.check(toggle);
    UserInteraction.open(panelTwo);

    assertTrue(panelTwo.isOpened());
    assertThrows(InteractionRejectedException.class, () -> UserInteraction.open(disabled));
  }
}
