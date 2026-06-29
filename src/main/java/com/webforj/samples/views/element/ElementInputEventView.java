package com.webforj.samples.views.element;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.element.event.ElementEventOptions;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/element/elementInputEvent.css")
@FrameTitle("Input Event")
public class ElementInputEventView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Element input = new Element("input");
  private final Div chips = new Div();

  public ElementInputEventView() {
    self.addClassName("element-demo-frame");

    Div card = new Div();
    card.addClassName("element-demo-card");

    input.addClassName("tag-input");
    input.setAttribute("type", "text");
    input.setAttribute("placeholder", "Type a tag and press Enter");

    chips.addClassName("tag-list");

    ElementEventOptions options =
        new ElementEventOptions()
            .addData("tag", "component.value")
            .setFilter("event.key === 'Enter'")
            .setCode("event.preventDefault(); component.value = '';");

    input.addEventListener(
        "keydown",
        e -> {
          String tag = String.valueOf(e.getEventMap().get("tag"));
          if (tag != null && !tag.isBlank()) {
            addChip(tag);
          }
        },
        options);

    card.add(new H2("Add tags"), input, chips);
    self.add(card);
  }

  private void addChip(String text) {
    Div chip = new Div(text);
    chip.addClassName("tag-chip");
    chips.add(chip);
  }
}
