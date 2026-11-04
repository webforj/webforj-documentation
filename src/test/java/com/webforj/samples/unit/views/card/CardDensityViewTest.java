package com.webforj.samples.unit.views.card;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.card.Card;
import com.webforj.samples.views.card.CardDensityView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class CardDensityViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new CardDensityView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldDivideTheRegionsOfBothInvoices(WebforjTestbed context) {
    context.add(new CardDensityView());

    ComponentLocator.of(Card.class).all().forEach(card -> assertTrue(card.isDivided()));
  }
}
