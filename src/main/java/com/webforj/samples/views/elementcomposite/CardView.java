package com.webforj.samples.views.elementcomposite;

import com.webforj.annotation.Attribute;
import com.webforj.annotation.JavaScript;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Component;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.element.ElementCompositeContainer;
import com.webforj.component.element.annotation.NodeName;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.ListEntry;
import com.webforj.component.html.elements.Span;
import com.webforj.component.html.elements.UnorderedList;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Pricing Cards")
public class CardView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CardView() {
    Card basic =
        buildPlanCard(
            "Basic Plan",
            "Perfect for individuals",
            "$9/month",
            "Choose Basic",
            "Up to 10 projects",
            "Email support",
            "Standard analytics");

    Card pro =
        buildPlanCard(
            "Pro Plan",
            "Best for growing teams",
            "$19/month",
            "Choose Pro",
            "Unlimited projects",
            "Priority email support",
            "Advanced analytics",
            "Custom integrations");

    self.setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .setStyle("min-height", "100vh")
        .setStyle("padding", "var(--dwc-space-l)")
        .setStyle("box-sizing", "border-box")
        .add(basic, pro);
  }

  private Card buildPlanCard(
      String title, String tagline, String price, String cta, String... features) {
    Card card = new Card();

    card.addToHeader(new H3(title));
    Span taglineSpan = new Span(tagline);
    taglineSpan.setStyle("display", "block");
    taglineSpan.setStyle("color", "var(--dwc-color-default-text-light)");
    card.addToHeader(taglineSpan);

    H4 priceH4 = new H4(price);
    priceH4.setStyle("color", "var(--dwc-color-primary)");
    priceH4.setStyle("margin", "var(--dwc-space-m) 0");
    card.add(priceH4);

    UnorderedList ul = new UnorderedList();
    ul.setStyle("list-style", "none");
    ul.setStyle("padding", "0");
    ul.setStyle("min-height", "7rem");
    for (String feature : features) {
      ul.add(buildFeature(feature));
    }
    card.add(ul);

    Button button = new Button(cta);
    button.setTheme(ButtonTheme.PRIMARY);
    button.setStyle("width", "100%");
    card.addToFooter(button);

    card.setStyle("--border-color", "var(--dwc-color-default)");
    card.setStyle("--border-radius", "var(--dwc-border-radius-m)");
    card.setStyle("--padding", "var(--dwc-space-m)");
    card.setStyle("--sl-panel-background-color", "var(--dwc-surface-3)");
    card.setStyle("width", "280px");

    return card;
  }

  private ListEntry buildFeature(String text) {
    Icon check = TablerIcon.create("check");
    check.setStyle("color", "var(--dwc-color-primary)");
    check.setStyle("margin-right", "var(--dwc-space-xs)");
    return new ListEntry(check, new Span(text));
  }

  @JavaScript(
      value =
          "https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/shoelace-autoloader.js",
      attributes = {@Attribute(name = "type", value = "module")})
  @StyleSheet("https://cdn.jsdelivr.net/npm/@shoelace-style/shoelace@2.20.1/cdn/themes/light.css")
  @NodeName("sl-card")
  public static final class Card extends ElementCompositeContainer
      implements HasClassName<Card>, HasStyle<Card> {

    public Card addToHeader(Component... components) {
      getElement().add("header", components);
      return this;
    }

    public Card addToFooter(Component... components) {
      getElement().add("footer", components);
      return this;
    }
  }
}
