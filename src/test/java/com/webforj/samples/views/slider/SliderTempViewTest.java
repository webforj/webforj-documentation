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
class SliderTempViewTest {
  @Test
  void shouldSetBothEndpointsWithTheButtons(WebforjTestbed context) {
    context.add(new SliderTempView());

    var slider = ComponentLocator.of(Slider.class).locate();
    var buttons = ComponentLocator.of(IconButton.class).all();

    assertEquals(60, slider.getMin());
    assertEquals(90, slider.getMax());

    UserInteraction.click(buttons.get(1));
    assertEquals(90, slider.getValue());

    UserInteraction.click(buttons.get(0));
    assertEquals(60, slider.getValue());
  }
}
