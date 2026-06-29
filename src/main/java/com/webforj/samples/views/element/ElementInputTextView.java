package com.webforj.samples.views.element;

import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Element Input Text")
public class ElementInputTextView extends Composite<Div> {
  private final Div self = getBoundComponent();

  public ElementInputTextView() {
    self.setStyle("display", "flex");
    self.setStyle("flex-direction", "column");
    self.setStyle("justify-content", "center");
    self.setStyle("align-items", "center");
    self.setStyle("min-height", "100vh");

    Div card = new Div();
    card.setStyle("width", "100%");
    card.setStyle("max-width", "420px");
    card.setStyle("padding", "var(--dwc-space-l)");
    card.setStyle("background", "var(--dwc-surface-3)");
    card.setStyle("border", "var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color)");
    card.setStyle("border-radius", "var(--dwc-border-radius-l)");

    Element figure = new Element("figure");
    figure.setAttribute("style", "margin: 0;");

    Element quote = new Element("blockquote");
    quote.setText("Building the entire UI in Java kept our team on one stack.");
    quote.setAttribute("style", "margin: 0 0 var(--dwc-space-s) 0; font-size: 1.125rem; font-style: italic;");

    Element caption = new Element("figcaption");
    caption.setHtml("<strong>Dana Lee</strong>, Engineering Lead");
    caption.setAttribute("style", "color: var(--dwc-color-default-text-light);");

    figure.add(quote, caption);

    card.add(new H2("Testimonial"), figure);
    self.add(card);
  }
}
