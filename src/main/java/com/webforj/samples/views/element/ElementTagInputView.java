package com.webforj.samples.views.element;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.element.event.ElementEventOptions;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/element/elementtaginput.css")
@FrameTitle("Tag Input")
public class ElementTagInputView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Element input = new Element("input");
  private final Div chips = new Div();

  public ElementTagInputView() {
    self.addClassName("element-demo-frame");

    Div card = new Div();
    card.addClassName("element-demo-card");

    Icon tagIcon = TablerIcon.create("tag");
    tagIcon.addClassName("tag-input-icon");

    input.addClassName("tag-input-field");
    input.setAttribute("type", "text");
    input.setAttribute("placeholder", "Type a tag and press Enter");

    Div field = new Div();
    field.addClassName("tag-input");
    field.add(tagIcon, input);

    chips.addClassName("tag-list");

    ElementEventOptions options =
        new ElementEventOptions()
            .addData("tag", "event.__tagValue")
            .setFilter("event.key === 'Enter'")
            .setCode(
                "event.preventDefault(); event.__tagValue = component.value; component.value = '';");

    input.addEventListener(
        "keydown",
        e -> {
          String tag = String.valueOf(e.getEventMap().get("tag"));
          if (tag != null && !tag.isBlank()) {
            addChip(tag);
          }
        },
        options);

    card.add(new H2("Add tags"), field, chips);
    self.add(card);
  }

  private void addChip(String text) {
    Div chip = new Div(text);
    chip.addClassName("tag-chip");
    chips.add(chip);
  }
}
