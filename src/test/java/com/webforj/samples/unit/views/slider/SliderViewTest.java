package com.webforj.samples.unit.views.slider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.icons.IconButton;
import com.webforj.component.slider.Slider;
import com.webforj.samples.views.slider.SliderView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SliderViewTest {
  @Test
  void shouldMuteTheVolumeWithTheMuteButton(WebforjTestbed context) {
    context.add(new SliderView());

    var slider = ComponentLocator.of(Slider.class).locate();

    UserInteraction.click(ComponentLocator.of(IconButton.class).byName("volume-off").locate());

    assertEquals(0, slider.getValue());
  }

  @Test
  void shouldTurnTheVolumeToMaxWithTheMaxButton(WebforjTestbed context) {
    context.add(new SliderView());

    var slider = ComponentLocator.of(Slider.class).locate();

    UserInteraction.click(ComponentLocator.of(IconButton.class).byName("volume-2").locate());

    assertEquals(100, slider.getValue());
  }
}
