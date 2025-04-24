package com.webforj.samples.views.element;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.element.event.ElementEventOptions;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@InlineStyleSheet("context://css/element/elementInputEvent.css")
@Route
@FrameTitle("Input Event")
public class ElementInputEventView extends Composite<Div> {

  Div label = new Div("Enter Text and Press Enter");
  Element input = new Element("input");

  public ElementInputEventView() {
    getBoundComponent().setStyle("margin", "20px");
    getBoundComponent().addClassName("frame");
    getBoundComponent().add(label, input);

    label.addClassName("element--label");
    input.addClassName("element--input");

    ElementEventOptions options = new ElementEventOptions();
    options.addData("theValue", "component.value");
    options.setFilter("event.key == 'Enter'");
    options.setCode("event.preventDefault();");

    input.addEventListener("keypress", e -> {
      showMessageDialog(e.getEventMap().get("theValue"),"Input Event");
    }, options);
  }
}