package com.webforj.samples.unit.views.card;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;

import com.webforj.samples.views.card.CardRegionsView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class CardRegionsViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new CardRegionsView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }
}
