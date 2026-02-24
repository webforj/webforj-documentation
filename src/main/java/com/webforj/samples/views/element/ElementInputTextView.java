package com.webforj.samples.views.element;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/element/elementInput.css")
@FrameTitle("Element Input Text")
public class ElementInputTextView extends Composite<Div> {
  private Div self = getBoundComponent();
  private Element input = new Element("input");

  public ElementInputTextView() {
    self.setStyle("margin", "20px")
        .add(input);

    input.addClassName("element--input")
            .setText("Here is the set text");
  }
}