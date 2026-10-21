package com.webforj.samples.views.slider;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.slider.SliderInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.slider.Slider;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

@WebforjTest
class SliderLabelsViewTest {
  @Test
  void shouldNameTheTemperatureTicksAndKeepTheNumbersOnTheOtherMajorTicks(WebforjTestbed context) {
    context.add(new SliderLabelsView());

    var slider = ComponentLocator.of(Slider.class).locate();

    assertEquals(
        Map.ofEntries(
            entry(0, "Cold"),
            entry(10, "10"),
            entry(20, "20"),
            entry(30, "Cool"),
            entry(40, "40"),
            entry(50, "Moderate"),
            entry(60, "60"),
            entry(70, "70"),
            entry(80, "Warm"),
            entry(90, "90"),
            entry(100, "Hot")),
        slider.getLabels());
    assertTrue(slider.isLabelsVisible());
  }

  @Test
  void shouldColorTheSliderByTheTemperatureBandTheUserSlidesTo(WebforjTestbed context) {
    context.add(new SliderLabelsView());

    var slider = ComponentLocator.of(Slider.class).locate();
    var themes = new ArrayList<Theme>();

    for (int value : List.of(10, 30, 50, 80)) {
      UserInteraction.perform(slider, SliderInteraction.slide(value));
      themes.add(slider.getTheme());
    }

    assertEquals(List.of(Theme.PRIMARY, Theme.SUCCESS, Theme.WARNING, Theme.DANGER), themes);
  }
}
