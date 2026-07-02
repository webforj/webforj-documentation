package com.webforj.samples.views.element;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/element/elementInput.css")
@FrameTitle("Input Demo")
public class ElementInputDemoView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Element input = new Element("input");

  public ElementInputDemoView() {
    self.setStyle("margin", "20px").add(input);

    input.addClassName("element--input").setAttribute("placeholder", "Enter some text");
  }
}
