package com.webforj.samples.views.flexlayout.container;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.event.ListSelectEvent;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.components.Box;

@Route
@StyleSheet("ws://css/flexlayout/container/flexContainerBuilder.css")
@FrameTitle("Flex Direction")
public class FlexDirectionView extends Composite<Div> {
  private static final int HUE = 36; // 360 / 10
  private Div self = getBoundComponent();
  private FlexLayout boxLayout;

  public FlexDirectionView() {
    FlexLayout mainLayout = FlexLayout.create()
            .horizontal()
            .build();

    this.boxLayout = FlexLayout.create()
            .horizontal()
            .build()
            .addClassName("button__container--single-row");

    for (int i = 1; i <= 4; i++) {
      String hue = String.valueOf(HUE * i);
      Box newBox = new Box(i);
      newBox.setStyle("background", "hsla(" + String.valueOf(hue) + ", 50%, 75%, 0.25)");
      newBox.setStyle("border", "2px solid " + "hsl(" + String.valueOf(hue) + ", 50%, 35%)");
      newBox.setStyle("color", "hsl(" + String.valueOf(hue) + ", 50%, 25%)");
      boxLayout.add(newBox);
    }

    ChoiceBox directions = new ChoiceBox()
            .addClassName("flex__options")
            .setLabel("Direction Options");
    directions.onSelect(this::selectDirection);
    for (FlexDirection justify : FlexDirection.values()) {
      String label = justify.getValue();
      String key = justify.toString().toLowerCase();
      String text = label.substring(0, 1).toUpperCase()
              + label.substring(1);
      directions.add("." + key + "()", text);
    }
    directions.selectIndex(0);
    self.add(mainLayout);
    mainLayout.add(directions, boxLayout);
  }

  private void selectDirection(ListSelectEvent<?> ev) {
    boxLayout.setDirection(FlexDirection.fromValue(ev.getSelectedItem().getText()));
  }
}
