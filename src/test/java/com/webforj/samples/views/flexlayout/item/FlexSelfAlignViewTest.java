package com.webforj.samples.views.flexlayout.item;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@WebforjTest
class FlexSelfAlignViewTest {
  @ParameterizedTest
  @CsvSource({
    "Stretch, STRETCH",
    "Flex-start, START",
    "Flex-end, END",
    "Center, CENTER",
    "Baseline, BASELINE",
    "Auto, AUTO"
  })
  void shouldAlignOnlyTheHighlightedItemAsPicked(
      String option, FlexAlignment alignment, WebforjTestbed context) {
    context.add(new FlexSelfAlignView());

    var alignments = ComponentLocator.of(ChoiceBox.class).locate();
    var items = ComponentLocator.of(FlexLayout.class).byClassName("button__container").locate();
    var highlighted = ComponentLocator.of(Button.class).within(items).byText("Align Me!").locate();
    var other = ComponentLocator.of(Button.class).within(items).byText("Button 1").locate();
    var otherAlignment = items.getItemAlignment(other);

    UserInteraction.select(alignments, alignments.find(option).orElseThrow());

    assertEquals(alignment, items.getItemAlignment(highlighted));
    assertEquals(otherAlignment, items.getItemAlignment(other));
  }
}
