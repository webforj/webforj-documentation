package com.webforj.samples.unit.views.slider;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.slider.Slider;
import com.webforj.samples.views.slider.SliderThemesView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class SliderThemesViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new SliderThemesView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowOneSliderPerBuiltInTheme(WebforjTestbed context) {
    context.add(new SliderThemesView());

    assertEquals(
        List.of(Theme.DEFAULT, Theme.DANGER, Theme.GRAY, Theme.INFO, Theme.SUCCESS, Theme.WARNING),
        ComponentLocator.of(Slider.class).all().stream().map(Slider::getTheme).toList());
  }
}
