package com.webforj.samples.unit.views.toast;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.toast.Toast;
import com.webforj.samples.views.toast.ToastThemeView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class ToastThemeViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var frame = context.getFrame();
    context.add(new ToastThemeView());

    verifyThat(frame).matchesSnapshot();
  }

  @Test
  void shouldCarryTheClassNameTheCustomThemeTargets(WebforjTestbed context) {
    context.getFrame();
    context.add(new ToastThemeView());

    var toast = ComponentLocator.of(Toast.class).locate();

    assertEquals(List.of("custom-theme"), toast.getClassNameList());
  }
}
