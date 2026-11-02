package com.webforj.samples.unit.views.accordion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.accordion.Accordion;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.component.button.Button;
import com.webforj.samples.views.accordion.AccordionMultipleView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class AccordionMultipleViewTest {
  @Test
  void shouldKeepPanelsAAndBOpenWhenTheUserOpensPanelC(WebforjTestbed context) {
    context.add(new AccordionMultipleView());

    var accordion = ComponentLocator.of(Accordion.class).locate();
    var panelC =
        ComponentLocator.of(AccordionPanel.class).within(accordion).byText("Panel C").locate();

    UserInteraction.open(panelC);

    assertEquals(
        List.of(true, true, true),
        ComponentLocator.of(AccordionPanel.class).within(accordion).all().stream()
            .map(AccordionPanel::isOpened)
            .toList());
  }

  @Test
  void shouldOpenEveryPanelWhenTheUserClicksOpenAll(WebforjTestbed context) {
    context.add(new AccordionMultipleView());

    var accordion = ComponentLocator.of(Accordion.class).locate();
    var panels = ComponentLocator.of(AccordionPanel.class).within(accordion).all();

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Open All").locate());

    assertTrue(panels.stream().allMatch(AccordionPanel::isOpened));
  }

  @Test
  void shouldCloseEveryPanelWhenTheUserClicksCloseAll(WebforjTestbed context) {
    context.add(new AccordionMultipleView());

    var accordion = ComponentLocator.of(Accordion.class).locate();
    var panels = ComponentLocator.of(AccordionPanel.class).within(accordion).all();

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Close All").locate());

    assertTrue(panels.stream().noneMatch(AccordionPanel::isOpened));
  }
}
