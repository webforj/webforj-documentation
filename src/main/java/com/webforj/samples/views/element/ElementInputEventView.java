package com.webforj.samples.views.element;

import com.webforj.component.Composite;
import com.webforj.component.element.Element;
import com.webforj.component.element.event.ElementEventOptions;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Input Event")
public class ElementInputEventView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final Element input = new Element("input");
  private final Div chips = new Div();

  public ElementInputEventView() {
    self.setStyle("display", "flex");
    self.setStyle("flex-direction", "column");
    self.setStyle("justify-content", "center");
    self.setStyle("align-items", "center");
    self.setStyle("min-height", "100vh");

    Div card = new Div();
    card.setStyle("width", "100%");
    card.setStyle("max-width", "420px");
    card.setStyle("padding", "var(--dwc-space-l)");
    card.setStyle("background", "var(--dwc-surface-3)");
    card.setStyle("border", "var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color)");
    card.setStyle("border-radius", "var(--dwc-border-radius-l)");

    input.setAttribute("type", "text");
    input.setAttribute("placeholder", "Type a tag and press Enter");
    input.setAttribute("style", "width: 100%; box-sizing: border-box; padding: var(--dwc-space-xs) var(--dwc-space-s); font-size: 1rem;");

    chips.setStyle("display", "flex");
    chips.setStyle("flex-wrap", "wrap");
    chips.setStyle("gap", "var(--dwc-space-xs)");
    chips.setStyle("margin-top", "var(--dwc-space-s)");

    ElementEventOptions options = new ElementEventOptions()
        .addData("tag", "component.value")
        .setFilter("event.key === 'Enter'")
        .setCode("event.preventDefault(); component.value = '';");

    input.addEventListener("keydown", e -> {
      String tag = String.valueOf(e.getEventMap().get("tag"));
      if (tag != null && !tag.isBlank()) {
        addChip(tag);
      }
    }, options);

    card.add(new H2("Add tags"), input, chips);
    self.add(card);
  }

  private void addChip(String text) {
    Div chip = new Div(text);
    chip.setStyle("padding", "var(--dwc-space-2xs) var(--dwc-space-s)");
    chip.setStyle("background", "var(--dwc-surface-2)");
    chip.setStyle("border", "var(--dwc-border-width) var(--dwc-border-style) var(--dwc-border-color)");
    chip.setStyle("border-radius", "var(--dwc-border-radius-pill)");
    chip.setStyle("font-size", "0.875rem");
    chips.add(chip);
  }
}