package com.webforj.samples.unit.views.flexlayout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.NumberField;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.samples.views.flexlayout.FlexBasisView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class FlexBasisViewTest {
  @Test
  void shouldSetTheTypedBasisOnTheSelectedBoxesOnly(WebforjTestbed context) {
    context.add(new FlexBasisView());

    var first = ComponentLocator.of(Button.class).byText("Box 1").locate();
    var third = ComponentLocator.of(Button.class).byText("Box 3").locate();
    var boxes =
        ComponentLocator.of(FlexLayout.class)
            .filter(layout -> layout.getComponents().contains(first))
            .locate();

    UserInteraction.click(first);
    UserInteraction.click(third);
    UserInteraction.type(ComponentLocator.of(NumberField.class).locate(), "150");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Set basis").locate());

    assertEquals(
        List.of("150.0px", "75px", "150.0px", "75px", "75px"),
        ComponentLocator.of(Button.class).within(boxes).all().stream()
            .map(boxes::getItemBasis)
            .toList());
  }

  @Test
  void shouldHighlightTheSelectedBoxesAndCountThemInTheTooltip(WebforjTestbed context) {
    context.add(new FlexBasisView());

    var first = ComponentLocator.of(Button.class).byText("Box 1").locate();
    var second = ComponentLocator.of(Button.class).byText("Box 2").locate();
    var setBasis = ComponentLocator.of(Button.class).byText("Set basis").locate();

    UserInteraction.click(first);
    UserInteraction.click(second);

    assertEquals(ButtonTheme.PRIMARY, first.getTheme());
    assertEquals("Set the basis for 2 box item(s)", setBasis.getTooltipText());

    UserInteraction.click(second);

    assertEquals(ButtonTheme.OUTLINED_PRIMARY, second.getTheme());
    assertEquals("Set the basis for 1 box item(s)", setBasis.getTooltipText());
  }

  @Test
  void shouldRestoreEveryBoxOnReset(WebforjTestbed context) {
    context.add(new FlexBasisView());

    var first = ComponentLocator.of(Button.class).byText("Box 1").locate();
    var boxes =
        ComponentLocator.of(FlexLayout.class)
            .filter(layout -> layout.getComponents().contains(first))
            .locate();
    var setBasis = ComponentLocator.of(Button.class).byText("Set basis").locate();

    UserInteraction.click(first);
    UserInteraction.type(ComponentLocator.of(NumberField.class).locate(), "150");
    UserInteraction.click(setBasis);
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Reset").locate());

    assertEquals("75px", boxes.getItemBasis(first));
    assertEquals(ButtonTheme.OUTLINED_PRIMARY, first.getTheme());
    assertEquals("Select a box item first", setBasis.getTooltipText());
  }
}
