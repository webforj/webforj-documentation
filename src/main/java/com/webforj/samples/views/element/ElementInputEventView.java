package com.webforj.samples.views.element;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.element.event.ElementEventOptions;
import com.webforj.component.html.elements.Div;
import com.webforj.component.optiondialog.MessageDialog;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/element/elementInputEvent.css")
@FrameTitle("Input Event")
public class ElementInputEventView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Div label = new Div("Enter Text and Press Enter");
  private final Element input = new Element("input");

  public ElementInputEventView() {
    self.setStyle("margin", "20px").addClassName("frame").add(label, input);

    label.addClassName("element--label");
    input.addClassName("element--input");

    ElementEventOptions options =
        new ElementEventOptions()
            .addData("theValue", "component.value")
            .setFilter("event.key == 'Enter'")
            .setCode("event.preventDefault();");

    input.addEventListener(
        "keypress",
        e -> {
          MessageDialog messageDialog =
              new MessageDialog(e.getEventMap().get("theValue"), "Input Event");
          messageDialog.setRawText(true);
          messageDialog.show();
        },
        options);
  }
}
