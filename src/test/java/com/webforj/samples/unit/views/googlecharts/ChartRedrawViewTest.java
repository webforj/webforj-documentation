package com.webforj.samples.unit.views.googlecharts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.field.NumberField;
import com.webforj.component.googlecharts.GoogleChart;
import com.webforj.component.toast.Toast;
import com.webforj.samples.views.googlecharts.ChartRedrawView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ChartRedrawViewTest {
  @Test
  void shouldRedrawTheChartWithTheTypedFollowerCounts(WebforjTestbed context) {
    context.add(new ChartRedrawView());

    UserInteraction.type(
        ComponentLocator.of(NumberField.class).byLabel("Value for Instagram").locate(), "250");
    UserInteraction.type(
        ComponentLocator.of(NumberField.class).byLabel("Value for LinkedIn").locate(), "900");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Redraw Chart").locate());

    assertEquals(
        List.of(
            List.of("Category", "Number of Followers in Thousands"),
            List.of("Instagram", 250L),
            List.of("Twitter", 100L),
            List.of("Facebook", 100L),
            List.of("LinkedIn", 900L)),
        ComponentLocator.of(GoogleChart.class).locate().getData());
  }

  @Test
  void shouldWarnAndKeepTheChartWhenAValueIsOutOfRange(WebforjTestbed context) {
    context.add(new ChartRedrawView());

    var chart = ComponentLocator.of(GoogleChart.class).locate();
    var shown = List.copyOf(chart.getData());

    UserInteraction.type(
        ComponentLocator.of(NumberField.class).byLabel("Value for Instagram").locate(), "1001");
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Redraw Chart").locate());

    assertEquals(shown, chart.getData());
    assertEquals(
        "Enter a valid number between 1 and 1000",
        ComponentLocator.of(Toast.class).locate().getText());
  }
}
