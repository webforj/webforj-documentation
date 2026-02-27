package com.webforj.samples.views.viewtransitions.components;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.event.ElementClickEvent;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconButton;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

@StyleSheet("ws://css/viewtransitions/components/notification-card.css")
public class NotificationCard extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final IconButton dismissBtn;

  public NotificationCard(FeatherIcon iconType, String title, String message) {
    self.setDirection(FlexDirection.ROW)
        .setAlignment(FlexAlignment.START)
        .setSpacing("var(--dwc-space-m)")
        .setPadding("var(--dwc-space-l)")
        .addClassName("notification-card");

    Icon icon = iconType.create();
    icon.addClassName("notification-icon");

    FlexLayout content = FlexLayout.create().vertical().build();
    content.setSpacing("var(--dwc-space-xs)").setStyle("flex", "1");

    Div heading = new Div();
    heading.addClassName("notification-title");
    heading.setText(title);

    Div body = new Div();
    body.addClassName("notification-message");
    body.setText(message);

    content.add(heading, body);

    dismissBtn = new IconButton(FeatherIcon.X.create());
    dismissBtn.addClassName("notification-dismiss");

    self.add(icon, content, dismissBtn);
  }

  public ListenerRegistration<ElementClickEvent<Icon>> onClose(
      EventListener<ElementClickEvent<Icon>> listener) {
    return dismissBtn.onClick(listener);
  }
}
