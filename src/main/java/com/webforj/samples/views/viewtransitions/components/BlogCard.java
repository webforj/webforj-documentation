package com.webforj.samples.views.viewtransitions.components;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.card.Card;
import com.webforj.component.element.event.ElementClickEvent;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

@BundleEntry("css/viewtransitions/components/blog-card.css")
public class BlogCard extends Composite<Card>
    implements HasClassName<BlogCard>, HasStyle<BlogCard> {
  private final Card self = getBoundComponent();

  public BlogCard(String title, String excerpt, String transitionName) {
    self.addClassName("blog-card");

    H4 heading = new H4(title);
    heading.setViewTransitionName("blog-title");

    Div image = new Div();
    image.addClassName("blog-image");
    image.setViewTransitionName(transitionName);

    Paragraph summary = new Paragraph(excerpt);
    summary.addClassName("blog-card-excerpt");

    self.addToTitle(heading).addToBody(image, summary);
  }

  public ListenerRegistration<ElementClickEvent<Card>> onClick(
      EventListener<ElementClickEvent<Card>> listener) {
    return self.onClick(listener);
  }
}
