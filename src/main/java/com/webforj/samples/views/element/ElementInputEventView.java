package com.webforj.samples.views.element;

import static com.webforj.component.optiondialog.OptionDialog.showMessageDialog;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.element.event.ElementEventOptions;
import com.webforj.component.html.elements.Div;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/element/elementInputEvent.css")
@FrameTitle("Input Event")
public class ElementInputEventView extends Composite<Div> {
  private Div self = getBoundComponent();
  private Div label = new Div("Enter Text and Press Enter");
  private Element input = new Element("input");

  public ElementInputEventView() {
    self.setStyle("margin", "20px")
        .addClassName("frame")
            .add(label, input);

    label.addClassName("element--label");
    input.addClassName("element--input");

    ElementEventOptions options = new ElementEventOptions()
            .addData("theValue", "component.value")
            .setFilter("event.key == 'Enter'")
            .setCode("event.preventDefault();");

    input.addEventListener("keypress", e -> {
      showMessageDialog(e.getEventMap().get("theValue"),"Input Event");
    }, options);
  }
}