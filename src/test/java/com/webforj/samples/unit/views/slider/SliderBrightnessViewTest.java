package com.webforj.samples.unit.views.slider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.icons.IconButton;
import com.webforj.component.slider.Slider;
import com.webforj.samples.views.slider.SliderBrightnessView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SliderBrightnessViewTest {
  @Test
  void shouldSetBothEndpointsWithTheButtons(WebforjTestbed context) {
    context.add(new SliderBrightnessView());

    var slider = ComponentLocator.of(Slider.class).locate();
    var buttons = ComponentLocator.of(IconButton.class).all();

    assertEquals(0, slider.getMin());
    assertEquals(100, slider.getMax());

    UserInteraction.click(buttons.get(1));
    assertEquals(100, slider.getValue());

    UserInteraction.click(buttons.get(0));
    assertEquals(0, slider.getValue());
  }
}
