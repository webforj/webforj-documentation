package com.webforj.samples.unit.views.toast;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.button.Button;
import com.webforj.component.toast.Toast;
import com.webforj.samples.views.toast.ToastView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class ToastViewTest {
  @Test
  void shouldAttachTheOpenedPersistentToastToTheFrameWithoutAContainer(WebforjTestbed context) {
    var frame = context.getFrame();
    context.add(new ToastView());

    var toast = ComponentLocator.of(Toast.class).locate();

    verifyThat(frame).hasComponent(toast);
    assertEquals(-1, toast.getDuration());
  }

  @Test
  void shouldCloseThePersistentToastWhenTheUserStopsIt(WebforjTestbed context) {
    context.getFrame();
    context.add(new ToastView());

    var toast = ComponentLocator.of(Toast.class).locate();

    UserInteraction.click(ComponentLocator.of(Button.class).within(toast).byText("Stop").locate());

    assertTrue(toast.isDestroyed());
  }
}
