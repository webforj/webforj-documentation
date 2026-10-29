package com.webforj.samples.unit.views.card;

import static com.webforj.testbed.assertion.WebforjAssertions.verifyThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Img;
import com.webforj.samples.views.card.CardOrientationView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;

@WebforjTest
class CardOrientationViewTest {
  @Test
  void shouldMatchTheSnapshot(WebforjTestbed context) {
    var view = new CardOrientationView();
    context.add(view);

    verifyThat(view).matchesSnapshot();
  }

  @Test
  void shouldPlaceTheAlbumArtBesideTheOtherRegions(WebforjTestbed context) {
    context.add(new CardOrientationView());

    var card = ComponentLocator.of(Card.class).locate();

    assertEquals(Card.Orientation.HORIZONTAL, card.getOrientation());
    assertTrue(ComponentLocator.of(Img.class).within(card).isPresent());
  }
}
