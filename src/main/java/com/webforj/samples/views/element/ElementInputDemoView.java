package com.webforj.samples.views.element;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/element/elementInput.css")
@FrameTitle("Input Demo")
public class ElementInputDemoView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Element meter = new Element("meter");

  public ElementInputDemoView() {
    self.addClassName("element-demo-frame");

    Div card = new Div();
    card.addClassName("element-demo-card");

    meter.addClassName("storage-meter");
    meter.setAttribute("min", "0");
    meter.setAttribute("max", "10");
    meter.setAttribute("low", "2");
    meter.setAttribute("high", "9");
    meter.setAttribute("optimum", "4");
    meter.setAttribute("value", "7.2");

    card.add(new H2("Storage"), meter, new Paragraph("7.2 GB of 10 GB used"));
    self.add(card);
  }
}
