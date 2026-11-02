package com.webforj.samples.unit.views.flexlayout.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.samples.views.flexlayout.item.FlexOrderView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class FlexOrderViewTest {
  @Test
  void shouldShowTheLastItemFirstWhenItGetsTheLowestOrder(WebforjTestbed context) {
    context.add(new FlexOrderView());

    var item = ComponentLocator.of(Button.class).byText("Order: 5").locate();
    var items =
        ComponentLocator.of(FlexLayout.class).byClassName("button__container--single-row").locate();

    UserInteraction.type(ComponentLocator.of(MaskedNumberField.class).locate(), "0");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Set Order").locate());

    assertEquals("Order: 0", item.getText());
    assertEquals(
        List.of(1, 2, 3, 4, 0),
        ComponentLocator.of(Button.class).within(items).all().stream()
            .map(items::getItemOrder)
            .toList());
  }

  @Test
  void shouldMarkAnEmptyOrderInvalid(WebforjTestbed context) {
    context.add(new FlexOrderView());

    var order = ComponentLocator.of(MaskedNumberField.class).locate();

    UserInteraction.type(order, "");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Set Order").locate());

    assertTrue(order.isInvalid());
  }
}
