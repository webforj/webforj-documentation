package com.webforj.samples.views.flexlayout.container;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.event.ListSelectEvent;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.components.Box;

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Flex Positioning")
public class FlexPositioningView extends Composite<Div> {
  private static final int HUE = 36; // 360 / 10
  private Div self = getBoundComponent();
  private FlexLayout boxLayout;

  public FlexPositioningView() {
    FlexLayout mainLayout = FlexLayout.create()
            .horizontal()
            .build();

    boxLayout = FlexLayout.create()
            .horizontal()
            .build()
            .addClassName("button__container--single-row")
            .setHeight("200px");

    for (int i = 1; i <= 4; i++) {
      String hue = String.valueOf(HUE * i);
      Box newBox = new Box(i);
      newBox.setStyle("background", "hsla(" + hue + ", 50%, 75%, 0.25)");
      newBox.setStyle("border", "2px solid " + "hsl(" + hue + ", 50%, 35%)");
      newBox.setStyle("color", "hsl(" + hue + ", 50%, 25%)");
      boxLayout.add(newBox);
    }

    ChoiceBox horizontal = new ChoiceBox()
            .addClassName("flex__options")
            .setLabel("Position Options");
    horizontal.onSelect(this::selectJustify);
    for (FlexJustifyContent justify : FlexJustifyContent.values()) {
      String label = justify.getValue();
      String key = justify.toString().toLowerCase();
      String text = label.substring(0, 1).toUpperCase()
              + label.substring(1);
      horizontal.add("." + key + "()", text);
    }
    horizontal.selectIndex(0);

    ChoiceBox vertical = new ChoiceBox()
            .addClassName("flex__options")
            .setLabel("Position Options");
    vertical.onSelect(this::selectAlignment);
    for (FlexAlignment justify : FlexAlignment.values()) {
      String label = justify.getValue();
      String key = justify.toString().toLowerCase();
      String text = label.substring(0, 1).toUpperCase()
              + label.substring(1);
      vertical.add("." + key + "()", text);
    }
    vertical.selectIndex(0);

    FlexLayout choices = FlexLayout.create(horizontal, vertical)
            .vertical()
            .build();

    self.add(mainLayout);
    mainLayout.add(choices, boxLayout);
  }

  private void selectJustify(ListSelectEvent<?> ev) {
    boxLayout.setJustifyContent(FlexJustifyContent.fromValue(ev.getSelectedItem().getText()));
  }

  private void selectAlignment(ListSelectEvent<?> ev) {
    boxLayout.setAlignment(FlexAlignment.fromValue(ev.getSelectedItem().getText()));
  }

}
