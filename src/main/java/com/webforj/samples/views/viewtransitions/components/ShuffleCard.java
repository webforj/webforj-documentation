package com.webforj.samples.views.viewtransitions.components;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;

@StyleSheet("ws://css/viewtransitions/components/shuffle-card.css")
public class ShuffleCard extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public ShuffleCard(String id, String title, String subtitle, String colorClass, FeatherIcon icon, int position) {
    self.setAlignment(FlexAlignment.CENTER)
        .setSpacing("var(--dwc-space-m)")
        .addClassName("shuffle-card", colorClass);
    self.setViewTransitionName("card-" + id);

    Div badge = new Div();
    badge.addClassName("shuffle-card-position");
    badge.setText("#" + position);

    Icon iconComponent = icon.create();
    iconComponent.addClassName("shuffle-card-icon");

    FlexLayout content = FlexLayout.create().vertical().build();
    content.setSpacing("0px")
        .setStyle("flex", "1");

    Div heading = new Div();
    heading.addClassName("shuffle-card-title");
    heading.setText(title);

    Div description = new Div();
    description.addClassName("shuffle-card-subtitle");
    description.setText(subtitle);

    content.add(heading, description);
    self.add(badge, iconComponent, content);
  }
}
