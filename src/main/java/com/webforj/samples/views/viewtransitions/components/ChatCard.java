package com.webforj.samples.views.viewtransitions.components;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.element.event.ElementClickEvent;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconButton;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

@StyleSheet("ws://css/viewtransitions/components/chat-card.css")
public class ChatCard extends Composite<Div> implements HasClassName<ChatCard>, HasStyle<ChatCard> {
  private Div self = getBoundComponent();
  private final IconButton closeBtn;

  public ChatCard() {
    self.setWidth("320px")
        .addClassName("chat-card");

    // Header
    FlexLayout header = FlexLayout.create().horizontal().build();
    header.addClassName("chat-header");

    Avatar avatar = new Avatar("Support");

    Div headerInfo = new Div();
    headerInfo.addClassName("chat-header-info");

    H4 name = new H4("Support Team");
    name.addClassName("chat-name");

    Span status = new Span("Online");
    status.addClassName("chat-status");

    headerInfo.add(name, status);

    closeBtn = new IconButton(FeatherIcon.X.create());
    closeBtn.addClassName("chat-close");

    header.add(avatar, headerInfo, closeBtn);

    // Content
    Div content = new Div();
    content.addClassName("chat-content");

    Paragraph greeting = new Paragraph("👋 Hi there!");
    greeting.addClassName("chat-greeting");

    Paragraph message = new Paragraph("How can we help you today?");
    message.addClassName("chat-message");

    content.add(greeting, message);

    // Actions
    FlexLayout actions = FlexLayout.create().horizontal().build();
    actions.addClassName("chat-actions");

    Button getStarted = new Button("Get Started", ButtonTheme.GRAY);
    getStarted.addClassName("chat-action-btn");

    Button learnMore = new Button("Learn More", ButtonTheme.OUTLINED_GRAY);
    learnMore.addClassName("chat-action-btn");

    actions.add(getStarted, learnMore);

    self.add(header, content, actions);
  }

  public ListenerRegistration<ElementClickEvent<Icon>> onClose(
      EventListener<ElementClickEvent<Icon>> listener) {
    return closeBtn.onClick(listener);
  }
}
