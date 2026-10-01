package com.webforj.samples.views.element;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.card.Card;
import com.webforj.component.element.Element;
import com.webforj.component.element.event.ElementEventOptions;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H2;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@BundleEntry("css/element/elementtaginput.css")
@FrameTitle("Tag Input")
public class ElementTagInputView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Card card = new Card();
  private final Element input = new Element("input");
  private final Div chips = new Div();

  public ElementTagInputView() {
    setCard();
    createInput();

    self.setHeight("100vh")
        .setAlignment(FlexAlignment.CENTER)
        .setJustifyContent(FlexJustifyContent.CENTER)
        .add(card);
  }

  private void setCard() {
    card.setWidth("100%")
        .setMaxWidth(420)
        .setExpanse(Expanse.XLARGE)
        .addToTitle(new H2("Add tags"));
  }

  private void createInput() {

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

    card.addToBody(field, chips);
  }

  private void addChip(String text) {
    Div chip = new Div(text);
    chip.addClassName("tag-chip");
    chips.add(chip);
  }
}
