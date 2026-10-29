package com.webforj.samples.unit.views.flexlayout;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.MaskedNumberField;
import com.webforj.component.field.TextField;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.samples.views.flexlayout.FlexLayoutView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class FlexLayoutViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new FlexLayoutView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldStackTheFieldRowsInAVerticalLayout(WebforjTestbed context) {
    context.add(new FlexLayoutView());

    var form = ComponentLocator.of(FlexLayout.class).byClassName("main__layout").locate();

    assertEquals(FlexDirection.COLUMN, form.getDirection());
    assertEquals(
        List.of(
            FlexDirection.ROW,
            FlexDirection.ROW,
            FlexDirection.ROW,
            FlexDirection.ROW,
            FlexDirection.ROW_REVERSE),
        form.getComponents().stream().map(row -> ((FlexLayout) row).getDirection()).toList());
  }

  @Test
  void shouldShareTheCityRowFortyTwentyFortyPercent(WebforjTestbed context) {
    context.add(new FlexLayoutView());

    var city = ComponentLocator.of(TextField.class).byLabel("City").locate();
    var state = ComponentLocator.of(ChoiceBox.class).byLabel("State").locate();
    var zip = ComponentLocator.of(MaskedNumberField.class).byLabel("Zip").locate();
    var row =
        ComponentLocator.of(FlexLayout.class)
            .filter(layout -> layout.getComponents().contains(city))
            .locate();

    assertEquals("40%", row.getItemBasis(city));
    assertEquals("20%", row.getItemBasis(state));
    assertEquals("40%", row.getItemBasis(zip));
  }
}
