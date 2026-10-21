package com.webforj.samples.views.slider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.icons.IconButton;
import com.webforj.component.slider.Slider;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SliderOrientationViewTest {
  @Test
  void shouldShowTheVolumeSliderVertically(WebforjTestbed context) {
    context.add(new SliderOrientationView());

    assertEquals(
        Slider.Orientation.VERTICAL, ComponentLocator.of(Slider.class).locate().getOrientation());
  }

  @Test
  void shouldMoveTheVolumeToItsEndsWithTheButtons(WebforjTestbed context) {
    context.add(new SliderOrientationView());

    var slider = ComponentLocator.of(Slider.class).locate();

    UserInteraction.click(ComponentLocator.of(IconButton.class).byName("volume-2").locate());
    assertEquals(100, slider.getValue());

    UserInteraction.click(ComponentLocator.of(IconButton.class).byName("volume-off").locate());
    assertEquals(0, slider.getValue());
  }
}
