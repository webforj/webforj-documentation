package com.webforj.samples.views.element;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/element/elementInput.css")
@Route("elementinputtext")
@FrameTitle("Element Input Text")
public class ElementInputTextView extends Composite<Div> {

  Element input = new Element("input");

  public ElementInputTextView() {
    getBoundComponent().setStyle("margin", "20px");
    getBoundComponent().add(input);

    input.addClassName("element--input");
    input.setText("Here is the set text");
  }
}