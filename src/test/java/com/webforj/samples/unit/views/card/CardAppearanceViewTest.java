package com.webforj.samples.unit.views.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.webforj.automation.act.UserInteraction;
import com.webforj.automation.find.ComponentLocator;
import com.webforj.component.card.Card;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.samples.views.card.CardAppearanceView;
import com.webforj.testbed.WebforjTestbed;
import com.webforj.testbed.annotation.WebforjTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@WebforjTest
class CardAppearanceViewTest {
  @Test
  void shouldDropTheBorderWhileTheBorderlessSwitchIsOn(WebforjTestbed context) {
    context.add(new CardAppearanceView());

    var card = ComponentLocator.of(Card.class).locate();
    var borderless = ComponentLocator.of(RadioButton.class).locate();

    assertFalse(card.isBorderless());

    UserInteraction.check(borderless);
    assertTrue(card.isBorderless());

    UserInteraction.uncheck(borderless);
    assertFalse(card.isBorderless());
  }

  @ParameterizedTest
  @EnumSource(Card.Shadow.class)
  void shouldPairThePickedShadowWithTheBorderlessCard(Card.Shadow shadow, WebforjTestbed context) {
    context.add(new CardAppearanceView());

    var card = ComponentLocator.of(Card.class).locate();

    UserInteraction.check(ComponentLocator.of(RadioButton.class).locate());
    UserInteraction.selectKey(ComponentLocator.of(ChoiceBox.class).locate(), shadow);

    assertEquals(shadow, card.getShadow());
    assertTrue(card.isBorderless());
  }
}
