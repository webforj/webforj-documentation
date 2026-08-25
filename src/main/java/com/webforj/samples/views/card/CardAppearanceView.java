package com.webforj.samples.views.card;

import com.webforj.component.Composite;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Card Appearance")
public class CardAppearanceView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  private final Card card = new Card();

  public CardAppearanceView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setHeight("100vh")
        .setSpacing("var(--dwc-space-l)");

    Span price = new Span("$29");
    price.setStyle("font-size", "var(--dwc-font-size-xl)");
    price.setStyle("font-weight", "var(--dwc-font-weight-semibold)");

    card.setShadow(Card.Shadow.MEDIUM);
    card.setWidth("100%");
    card.setMaxWidth("20rem");
    card.addToTitle(new H3("Team"));
    card.addToBody(price, new Paragraph("Unlimited projects"));

    self.add(buildControls(), card);
  }

  private FlexLayout buildControls() {
    RadioButton borderless = RadioButton.Switch("borderless", card.isBorderless());
    borderless.onToggle(ev -> card.setBorderless(ev.isToggled()));

    ChoiceBox shadow = new ChoiceBox("shadow");
    for (Card.Shadow value : Card.Shadow.values()) {
      shadow.add(value, value.name().toLowerCase());
    }
    shadow.selectKey(card.getShadow());
    shadow.onSelect(ev -> card.setShadow((Card.Shadow) ev.getSelectedItem().getKey()));

    FlexLayout controls = FlexLayout.create(borderless, shadow).horizontal().build();
    controls.setSpacing("var(--dwc-space-l");
    controls.setAlignment(FlexAlignment.CENTER);

    return controls;
  }
}
