package com.webforj.samples.views.element;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/element/elementfigure.css")
@FrameTitle("Element Input Text")
public class ElementFigureView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public ElementFigureView() {
    self.addClassName("element-demo-frame");

    Div card = new Div();
    card.addClassName("element-demo-card");

    Element figure = new Element("figure");
    figure.addClassName("testimonial-figure");

    Element quote = new Element("blockquote");
    quote.addClassName("testimonial-quote");
    quote.setText("Building the entire UI in Java kept our team on one stack.");

    Element caption = new Element("figcaption");
    caption.addClassName("testimonial-caption");
    caption.setHtml("<strong>Dana Lee</strong>, Engineering Lead");

    figure.add(quote, caption);

    card.add(new H2("Testimonial"), figure);
    self.add(card);
  }
}
