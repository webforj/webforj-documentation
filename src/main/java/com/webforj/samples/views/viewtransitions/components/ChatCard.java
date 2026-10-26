package com.webforj.samples.views.viewtransitions.components;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.element.event.ElementClickEvent;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconButton;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

@BundleEntry("css/viewtransitions/components/chat-card.css")
public class ChatCard extends Composite<Card>
    implements HasClassName<ChatCard>, HasStyle<ChatCard> {
  private final Card self = getBoundComponent();
  private final IconButton closeBtn;

  public ChatCard() {
    self.setWidth("320px");

    // Header
    self.setDivided(true);
    Avatar avatar = new Avatar("Support");
    self.addToIcon(avatar);

    H4 name = new H4("Support Team");
    self.addToTitle(name);

    Span status = new Span("Online");
    status.addClassName("chat-status");
    self.addToCaption(status);

    closeBtn = new IconButton(FeatherIcon.X.create());
    closeBtn.addClassName("chat-close");

    self.addToHeaderActions(closeBtn);

    // Content

    Paragraph greeting = new Paragraph("👋 Hi there!");
    greeting.addClassName("chat-greeting");

    Paragraph message = new Paragraph("How can we help you today?");
    message.addClassName("chat-message");

    self.addToBody(greeting, message);
    // Actions

    Button getStarted = new Button("Get Started", ButtonTheme.GRAY);
    Button learnMore = new Button("Learn More", ButtonTheme.OUTLINED_GRAY);

    FlexLayout actions = new FlexLayout(getStarted, learnMore);
    actions.setWidth("100%");
    actions.setJustifyContent(FlexJustifyContent.CENTER);

    self.addToFooter(actions);
  }

  public ListenerRegistration<ElementClickEvent<Icon>> onClose(
      EventListener<ElementClickEvent<Icon>> listener) {
    return closeBtn.onClick(listener);
  }
}
