package com.webforj.samples.unit.views.flexlayout.container;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.samples.views.flexlayout.container.FlexDirectionView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class FlexDirectionViewTest {
  @ParameterizedTest
  @CsvSource({"Row-reverse, ROW_REVERSE", "Column, COLUMN", "Column-reverse, COLUMN_REVERSE"})
  void shouldLayTheBoxesOutInThePickedDirection(
      String option, FlexDirection direction, WebforjTestbed context) {
    context.add(new FlexDirectionView());

    var directions = ComponentLocator.of(ChoiceBox.class).locate();
    var boxes =
        ComponentLocator.of(FlexLayout.class).byClassName("button__container--single-row").locate();

    UserInteraction.select(directions, directions.find(option).orElseThrow());

    assertEquals(direction, boxes.getDirection());
  }

  @Test
  void shouldLayTheBoxesOutInARowAgain(WebforjTestbed context) {
    context.add(new FlexDirectionView());

    var directions = ComponentLocator.of(ChoiceBox.class).locate();
    var boxes =
        ComponentLocator.of(FlexLayout.class).byClassName("button__container--single-row").locate();

    UserInteraction.select(directions, directions.find("Column").orElseThrow());
    UserInteraction.select(directions, directions.find("Row").orElseThrow());

    assertEquals(FlexDirection.ROW, boxes.getDirection());
  }
}
