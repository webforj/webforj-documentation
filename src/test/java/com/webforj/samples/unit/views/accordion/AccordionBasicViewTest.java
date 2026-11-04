package com.webforj.samples.unit.views.accordion;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.accordion.AccordionPanel;
import com.webforj.samples.views.accordion.AccordionBasicView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class AccordionBasicViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new AccordionBasicView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldKeepSectionOneOpenWhenTheUserOpensSectionTwo(WebforjTestbed context) {
    context.add(new AccordionBasicView());

    var sectionOne = ComponentLocator.of(AccordionPanel.class).byText("Section One").locate();
    var sectionTwo = ComponentLocator.of(AccordionPanel.class).byText("Section Two").locate();

    UserInteraction.open(sectionTwo);

    assertTrue(sectionTwo.isOpened());
    assertTrue(sectionOne.isOpened());
  }

  @Test
  void shouldLeaveTheOtherSectionsAsTheyAreWhenTheUserClosesSectionOne(WebforjTestbed context) {
    context.add(new AccordionBasicView());

    var sectionOne = ComponentLocator.of(AccordionPanel.class).byText("Section One").locate();
    var sectionTwo = ComponentLocator.of(AccordionPanel.class).byText("Section Two").locate();
    UserInteraction.open(sectionTwo);

    UserInteraction.close(sectionOne);

    assertFalse(sectionOne.isOpened());
    assertTrue(sectionTwo.isOpened());
  }
}
