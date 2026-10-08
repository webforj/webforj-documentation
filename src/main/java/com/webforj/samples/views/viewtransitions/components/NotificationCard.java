package com.webforj.samples.views.viewtransitions.components;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.card.Card;
import com.webforj.component.card.Card.Orientation;
import com.webforj.component.element.event.ElementClickEvent;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconButton;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

@BundleEntry("css/viewtransitions/components/notification-card.css")
public class NotificationCard extends Composite<Card> {
  private final Card self = getBoundComponent();
  private final IconButton dismissBtn;

  public NotificationCard(FeatherIcon iconType, String title, String message) {
    self.setOrientation(Orientation.HORIZONTAL)
        .setBorderless(true)
        .setWidth(360)
        .addClassName("notification-card");

    Icon icon = iconType.create();

    Div heading = new Div();
    heading.setText(title);

    Div body = new Div();
    body.setText(message);

    dismissBtn = new IconButton(FeatherIcon.X.create());
    dismissBtn.addClassName("notification-dismiss");

    self.addToIcon(icon).addToTitle(heading).addToCaption(body).addToHeaderActions(dismissBtn);
  }

  public ListenerRegistration<ElementClickEvent<Icon>> onClose(
      EventListener<ElementClickEvent<Icon>> listener) {
    return dismissBtn.onClick(listener);
  }
}
