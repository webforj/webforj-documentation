package com.webforj.samples.unit.views.spinner;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.spinner.Spinner;
import com.webforj.component.spinner.SpinnerExpanse;
import com.webforj.samples.views.spinner.SpinnerExpanseDemoView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@WebforjTest
class SpinnerExpanseDemoViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new SpinnerExpanseDemoView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldShowTheSmallMediumAndLargeExpanses(WebforjTestbed context) {
    context.add(new SpinnerExpanseDemoView());

    assertEquals(
        List.of(SpinnerExpanse.SMALL, SpinnerExpanse.MEDIUM, SpinnerExpanse.LARGE),
        ComponentLocator.of(Spinner.class).all().stream().map(Spinner::getExpanse).toList());
  }
}
