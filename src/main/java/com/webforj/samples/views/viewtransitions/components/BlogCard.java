package com.webforj.samples.views.viewtransitions.components;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.event.ElementClickEvent;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

@StyleSheet("ws://css/viewtransitions/components/blog-card.css")
public class BlogCard extends Composite<FlexLayout> implements HasClassName<BlogCard>, HasStyle<BlogCard> {
  private FlexLayout self = getBoundComponent();

  public BlogCard(String title, String excerpt, String transitionName) {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setPadding("var(--dwc-space-m)")
        .addClassName("blog-card");

    H4 heading = new H4(title);
    heading.addClassName("blog-card-title");
    heading.setViewTransitionName("blog-title");

    Div image = new Div();
    image.addClassName("blog-image");
    image.setViewTransitionName(transitionName);

    Paragraph summary = new Paragraph(excerpt);
    summary.addClassName("blog-card-excerpt");

    self.add(heading, image, summary);
  }

  public ListenerRegistration<ElementClickEvent<FlexLayout>> onClick(
      EventListener<ElementClickEvent<FlexLayout>> listener) {
    return self.onClick(listener);
  }
}
