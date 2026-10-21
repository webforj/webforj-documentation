package com.webforj.samples.unit.views.slider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.field.NumberField;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.slider.Slider;
import com.webforj.samples.views.slider.SliderTickSpacingView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SliderTickSpacingViewTest {
  @Test
  void shouldSpaceTheMajorTicksAsTypedInTheMajorTickField(WebforjTestbed context) {
    context.add(new SliderTickSpacingView());

    var slider = ComponentLocator.of(Slider.class).locate();

    UserInteraction.type(
        ComponentLocator.of(NumberField.class).byLabel("Major Tick").locate(), "25");

    assertEquals(25, slider.getMajorTickSpacing());
  }

  @Test
  void shouldSpaceTheMinorTicksAsTypedInTheMinorTickField(WebforjTestbed context) {
    context.add(new SliderTickSpacingView());

    var slider = ComponentLocator.of(Slider.class).locate();

    UserInteraction.type(
        ComponentLocator.of(NumberField.class).byLabel("Minor Tick").locate(), "5");

    assertEquals(5, slider.getMinorTickSpacing());
  }

  @Test
  void shouldSnapToTheTicksWhenTheSnapSwitchIsOn(WebforjTestbed context) {
    context.add(new SliderTickSpacingView());

    var slider = ComponentLocator.of(Slider.class).locate();

    UserInteraction.check(ComponentLocator.of(RadioButton.class).byText("Snap to Ticks").locate());

    assertTrue(slider.isSnapToTicks());
  }

  @Test
  void shouldHideTheTicksWhenTheShowTicksSwitchIsOff(WebforjTestbed context) {
    context.add(new SliderTickSpacingView());

    var slider = ComponentLocator.of(Slider.class).locate();

    UserInteraction.uncheck(ComponentLocator.of(RadioButton.class).byText("Show Ticks").locate());

    assertFalse(slider.isTicksVisible());
  }
}
