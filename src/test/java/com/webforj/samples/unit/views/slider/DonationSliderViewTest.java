package com.webforj.samples.unit.views.slider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.act.component.slider.SliderInteraction;
import com.webforj.automation.act.exception.InteractionRejectedException;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.slider.Slider;
import com.webforj.component.toast.Toast;
import com.webforj.samples.views.slider.DonationSliderView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

@WebforjTest
class DonationSliderViewTest {
  @Test
  void shouldNotLetTheUserSlidePastTheCustomMaximumOf80(WebforjTestbed context) {
    context.add(new DonationSliderView());

    Slider slider = ComponentLocator.of(Slider.class).locate();
    Consumer<Slider> slideTo90 = SliderInteraction.slide(90);

    assertThrows(
        InteractionRejectedException.class, () -> UserInteraction.perform(slider, slideTo90));
  }

  @Test
  void shouldConfirmTheDonationTheUserSlidTo(WebforjTestbed context) {
    context.add(new DonationSliderView());

    var slider = ComponentLocator.of(Slider.class).locate();

    UserInteraction.perform(slider, SliderInteraction.slide(80));
    UserInteraction.click(ComponentLocator.of(Button.class).byText("Confirm Donation").locate());

    assertEquals(
        "Thank you for your generous contribution of $80!",
        ComponentLocator.of(Toast.class).locate().getText());
  }

  @Test
  void shouldConfirmTheInitialDonationOf50WithoutSliding(WebforjTestbed context) {
    context.add(new DonationSliderView());

    UserInteraction.click(ComponentLocator.of(Button.class).byText("Confirm Donation").locate());

    assertEquals(
        "Thank you for your generous contribution of $50!",
        ComponentLocator.of(Toast.class).locate().getText());
  }
}
