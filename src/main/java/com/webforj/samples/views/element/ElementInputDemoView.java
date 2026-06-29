package com.webforj.samples.views.element;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/element/elementInput.css")
@FrameTitle("Input Demo")
public class ElementInputDemoView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Element meter = new Element("meter");

  public ElementInputDemoView() {
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

    meter.addClassName("storage-meter");
    meter.setAttribute("min", "0");
    meter.setAttribute("max", "10");
    meter.setAttribute("low", "2");
    meter.setAttribute("high", "9");
    meter.setAttribute("optimum", "4");
    meter.setAttribute("value", "7.2");
    meter.setAttribute("style", "width: 100%; box-sizing: border-box; height: 1rem;");

    card.add(new H2("Storage"), meter, new Paragraph("7.2 GB of 10 GB used"));
    self.add(card);
  }
}