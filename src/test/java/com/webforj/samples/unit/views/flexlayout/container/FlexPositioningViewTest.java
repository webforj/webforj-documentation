package com.webforj.samples.unit.views.flexlayout.container;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.samples.views.flexlayout.container.FlexPositioningView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class FlexPositioningViewTest {
  @ParameterizedTest
  @CsvSource({
    "Flex-end, END",
    "Center, CENTER",
    "Space-between, BETWEEN",
    "Space-around, AROUND",
    "Space-evenly, EVENLY"
  })
  void shouldPositionTheBoxesHorizontallyAsPicked(
      String option, FlexJustifyContent justification, WebforjTestbed context) {
    context.add(new FlexPositioningView());

    var horizontal = ComponentLocator.of(ChoiceBox.class).all().get(0);
    var boxes =
        ComponentLocator.of(FlexLayout.class).byClassName("button__container--single-row").locate();

    UserInteraction.select(horizontal, horizontal.find(option).orElseThrow());

    assertEquals(justification, boxes.getJustifyContent());
  }

  @ParameterizedTest
  @CsvSource({
    "Flex-start, START",
    "Flex-end, END",
    "Center, CENTER",
    "Baseline, BASELINE",
    "Auto, AUTO"
  })
  void shouldPositionTheBoxesVerticallyAsPicked(
      String option, FlexAlignment alignment, WebforjTestbed context) {
    context.add(new FlexPositioningView());

    var vertical = ComponentLocator.of(ChoiceBox.class).all().get(1);
    var boxes =
        ComponentLocator.of(FlexLayout.class).byClassName("button__container--single-row").locate();

    UserInteraction.select(vertical, vertical.find(option).orElseThrow());

    assertEquals(alignment, boxes.getAlignment());
  }
}
