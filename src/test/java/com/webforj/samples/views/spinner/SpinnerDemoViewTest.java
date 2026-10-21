package com.webforj.samples.views.spinner;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.Theme;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class SpinnerDemoViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new SpinnerDemoView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowThePrimaryThemeAndTheSmallestExpanseSetOnTheSpinner(WebforjTestbed context) {
    context.add(new SpinnerDemoView());

    var spinner = ComponentLocator.of(Spinner.class).locate();

    assertEquals(Theme.PRIMARY, spinner.getTheme());
    assertEquals(SpinnerExpanse.XXXSMALL, spinner.getExpanse());
  }
}
