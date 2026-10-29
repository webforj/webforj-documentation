package com.webforj.samples.unit.views.elementcomposite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.toast.Toast;
import com.webforj.samples.views.elementcomposite.RatingView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

@WebforjTest
class RatingViewTest {
  @Test
  void shouldEnableSubmissionForPositiveRatingsAndResetAfterSubmission(WebforjTestbed context) {
    context.add(new RatingView());

    var rating = ComponentLocator.of(RatingView.Rating.class).locate();
    var submit = ComponentLocator.of(Button.class).byText("Submit review").locate();

    assertFalse(submit.isEnabled());

    UserInteraction.perform(rating, rate(4));
    assertTrue(submit.isEnabled());

    UserInteraction.perform(rating, rate(0));
    assertFalse(submit.isEnabled());

    UserInteraction.perform(rating, rate(3));
    assertTrue(submit.isEnabled());

    UserInteraction.click(submit);

    assertFalse(submit.isEnabled());
    assertEquals("Thank you for your review!", ComponentLocator.of(Toast.class).locate().getText());
  }

  private static Consumer<RatingView.Rating> rate(int stars) {
    return rating -> {
      rating.setValue(stars);
      UserInteraction.fireEvent(new RatingView.ChangeEvent(rating, Map.of("value", stars)));
    };
  }
}
