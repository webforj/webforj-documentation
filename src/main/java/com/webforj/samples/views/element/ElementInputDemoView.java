package com.webforj.samples.views.element;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/element/elementInput.css")
@FrameTitle("Input Demo")
public class ElementInputDemoView extends Composite<Div> {
  private Div self = getBoundComponent();
  private Element input = new Element("input");

  public ElementInputDemoView() {
    self.setStyle("margin", "20px")
        .add(input);

    input.addClassName("element--input")
        .setAttribute("placeholder", "Enter some text");
  }
}