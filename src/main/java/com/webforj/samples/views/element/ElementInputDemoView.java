package com.webforj.samples.views.element;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.config.RouteConfig;

@InlineStyleSheet("context://css/element/elementInput.css")
@Route(RouteConfig.ELEMENT_INPUT_DEMO)
@FrameTitle("Input Demo")
public class ElementInputDemoView extends Composite<Div> {

  Element input = new Element("input");

  public ElementInputDemoView() {
    getBoundComponent().setStyle("margin", "20px");
    getBoundComponent().add(input);

    input.addClassName("element--input");
    input.setAttribute("placeholder", "Enter some text");
  }
}