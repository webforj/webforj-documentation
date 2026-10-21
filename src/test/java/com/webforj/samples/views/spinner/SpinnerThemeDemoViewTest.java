package com.webforj.samples.views.spinner;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.spinner.Spinner;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class SpinnerThemeDemoViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new SpinnerThemeDemoView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowOneSpinnerPerBuiltInTheme(WebforjTestbed context) {
    context.add(new SpinnerThemeDemoView());

    assertEquals(
        List.of(
            Theme.DEFAULT,
            Theme.PRIMARY,
            Theme.SUCCESS,
            Theme.DANGER,
            Theme.WARNING,
            Theme.GRAY,
            Theme.INFO),
        ComponentLocator.of(Spinner.class).all().stream().map(Spinner::getTheme).toList());
  }
}
