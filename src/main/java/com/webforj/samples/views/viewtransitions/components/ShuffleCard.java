package com.webforj.samples.views.viewtransitions.components;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.card.Card;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;

@BundleEntry("css/viewtransitions/components/shuffle-card.css")
public class ShuffleCard extends Composite<Card> {
  private final Card self = getBoundComponent();

  public ShuffleCard(
      String id, String title, String subtitle, String colorClass, FeatherIcon icon, int position) {
    self.addClassName("shuffle-card", colorClass);
    self.setViewTransitionName("card-" + id);
    self.setBorderless(true);

    Div badge = new Div();
    badge.addClassName("shuffle-card-position");
    badge.setText("#" + position);

    Icon iconComponent = icon.create();
    iconComponent.addClassName("shuffle-card-icon");

    Div heading = new Div();
    heading.addClassName("shuffle-card-title");
    heading.setText(title);

    Div description = new Div();
    description.addClassName("shuffle-card-subtitle");
    description.setText(subtitle);

    self.addToTitle(heading)
        .addToHeaderActions(badge)
        .addToIcon(iconComponent)
        .addToCaption(description);
  }
}
