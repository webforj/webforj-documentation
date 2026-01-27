package com.webforj.samples.views.viewtransitions;

import com.webforj.samples.views.viewtransitions.components.ChatCard;
import com.webforj.samples.views.viewtransitions.components.DemoHeader;
import com.webforj.Page;
import com.webforj.ViewTransition;
import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Chat Widget")
@StyleSheet("ws://css/viewtransitions/chat.css")
public class ViewTransitionChatView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Div cardContainer;
  private Button chatButton;
  private ChatCard currentCard;
  private boolean isOpen = false;

  public ViewTransitionChatView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .setSpacing("var(--dwc-space-l)")
        .setHeight("100vh");

    DemoHeader header = new DemoHeader(
        "Chat Widget",
        "Click the button to toggle the chat panel with zoom transitions.",
        "--dwc-color-primary"
    );

    cardContainer = new Div();
    cardContainer.setMinHeight("200px");

    chatButton = new Button(FeatherIcon.MESSAGE_CIRCLE.create());
    chatButton
        .setExpanse(Expanse.NONE)
        .setTheme(ButtonTheme.PRIMARY)
        .setSize(65, 65)
        .onClick(e -> toggleChat());

    Div buttonWrapper = new Div();
    buttonWrapper.addClassName("chat-toggle-btn");
    buttonWrapper.add(chatButton);

    self.add(header, cardContainer, buttonWrapper);
    whenAttached().thenAccept(e -> toggleChat());
  }

  private void toggleChat() {
    if (isOpen) {
      closeChat();
    } else {
      openChat();
    }
  }

  private void openChat() {
    if (isOpen)
      return;

    currentCard = new ChatCard();
    currentCard.onClose(e -> closeChat());

    Page.getCurrent().startViewTransition()
        .enter(currentCard, ViewTransition.ZOOM)
        .onUpdate(done -> {
          cardContainer.add(currentCard);
          isOpen = true;
          done.run();
        })
        .start();
  }

  private void closeChat() {
    if (!isOpen || currentCard == null)
      return;

    ChatCard cardToRemove = currentCard;

    Page.getCurrent().startViewTransition()
        .exit(cardToRemove, ViewTransition.ZOOM)
        .onUpdate(done -> {
          cardContainer.remove(cardToRemove);
          currentCard = null;
          isOpen = false;
          done.run();
        })
        .start();
  }
}
