package com.webforj.samples.views.card;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Pricing Plans Card")
@BundleEntry("css/card/cardAppearance.css")
public class CardAppearanceView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CardAppearanceView() {
    self.setSpacing("var(--dwc-space-l")
        .setWrap(FlexWrap.WRAP)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setAlignment(FlexAlignment.CENTER)
        .addClassName("card-demo");

    Card starter = buildPlan("Starter", "$0", "Up to 3 projects");
    starter.setShadow(Card.Shadow.NONE);

    Card team = buildPlan("Team", "29", "Unlimited Projects");
    team.setShadow(Card.Shadow.LARGE).setBorderless(true);

    Card enterprise = buildPlan("Enterprise", "Custom", "Dedicated Support");
    enterprise.setShadow(Card.Shadow.NONE).setBorderless(true);

    self.add(starter, team, enterprise);
  }

  private Card buildPlan(String name, String price, String detail) {
    Span amount = new Span(price).addClassName("card-appearance__price");

    Card card =
        new Card(
            FlexLayout.create(amount, new Paragraph(detail))
                .vertical()
                .build()
                .setSpacing("var(--dwc-space-xs"));

    return card.addToTitle(new H3(name))
        .addToFooter(new Button("Choose plan", ButtonTheme.OUTLINED_PRIMARY))
        .setWidth("220px");
  }
}
