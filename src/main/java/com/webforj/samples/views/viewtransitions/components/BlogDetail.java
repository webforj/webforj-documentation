package com.webforj.samples.views.viewtransitions.components;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.card.Card;
import com.webforj.component.element.event.ElementClickEvent;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.IconButton;
import com.webforj.concern.HasClassName;
import com.webforj.concern.HasStyle;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;

@BundleEntry("css/viewtransitions/components/blog-card.css")
public class BlogDetail extends Composite<Card>
    implements HasClassName<BlogDetail>, HasStyle<BlogDetail> {
  private final Card self = getBoundComponent();
  private final IconButton closeBtn;

  public BlogDetail(String title, String fullText, String transitionName) {
    self.addClassName("blog-detail").setExpanse(Expanse.LARGE);

    H4 heading = new H4(title);
    heading.setViewTransitionName("blog-title");

    closeBtn = new IconButton(FeatherIcon.X.create());
    closeBtn.addClassName("blog-detail-close");

    Div image = new Div();
    image.addClassName("blog-image");
    image.setViewTransitionName(transitionName);

    Paragraph body = new Paragraph(fullText);
    body.addClassName("blog-detail-text");

    self.addToTitle(heading).addToHeaderActions(closeBtn).addToBody(image, body);
  }

  public ListenerRegistration<ElementClickEvent<Icon>> onClose(
      EventListener<ElementClickEvent<Icon>> listener) {
    return closeBtn.onClick(listener);
  }
}
