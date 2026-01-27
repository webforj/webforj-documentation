package com.webforj.samples.views.viewtransitions.components;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.event.ElementClickEvent;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconButton;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

@StyleSheet("ws://css/viewtransitions/components/blog-card.css")
public class BlogDetail extends Composite<FlexLayout> implements HasClassName<BlogDetail>, HasStyle<BlogDetail> {
  private FlexLayout self = getBoundComponent();
  private final IconButton closeBtn;

  public BlogDetail(String title, String fullText, String transitionName) {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setPadding("var(--dwc-space-m)")
        .addClassName("blog-detail");

    FlexLayout header = FlexLayout.create()
        .horizontal()
        .align().center()
        .justify().between()
        .build();

    H4 heading = new H4(title);
    heading.addClassName("blog-detail-title");
    heading.setViewTransitionName("blog-title");

    closeBtn = new IconButton(FeatherIcon.X.create());

    header.add(heading, closeBtn);

    Div image = new Div();
    image.addClassName("blog-image");
    image.setViewTransitionName(transitionName);

    Paragraph body = new Paragraph(fullText);
    body.addClassName("blog-detail-text");

    self.add(header, image, body);
  }

  public ListenerRegistration<ElementClickEvent<Icon>> onClose(
      EventListener<ElementClickEvent<Icon>> listener) {
    return closeBtn.onClick(listener);
  }
}
