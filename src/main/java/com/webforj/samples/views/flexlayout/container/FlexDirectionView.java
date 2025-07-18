package com.webforj.samples.views.flexlayout.container;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.list.event.ListSelectEvent;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import com.webforj.samples.components.Box;

@InlineStyleSheet("context://css/flexlayout/container/flexContainerBuilder.css")
@Route("flexdirection")
@FrameTitle("Flex Direction")
public class FlexDirectionView extends Composite<Div> {

  FlexLayout boxLayout;

  public FlexDirectionView() {

    FlexLayout mainLayout = FlexLayout.create()
        .horizontal()
        .build();

    this.boxLayout = FlexLayout.create()
        .horizontal()
        .build()
        .addClassName("button__container--single-row");

    for (int i = 1; i <= 4; i++) {
      String hue = String.valueOf((360 / 10) * i);
      Box newBox = new Box(i);
      newBox.setStyle("background", "hsla(" + String.valueOf(hue) + ", 50%, 75%, 0.25)");
      newBox.setStyle("border", "2px solid " + "hsl(" + String.valueOf(hue) + ", 50%, 35%)");
      newBox.setStyle("color", "hsl(" + String.valueOf(hue) + ", 50%, 25%)");
      boxLayout.add(newBox);
    }

    ChoiceBox directions = new ChoiceBox();
    directions.onSelect(this::selectDirection);
    directions.addClassName("flex__options");
    directions.setLabel("Direction Options");
    for (FlexDirection justify : FlexDirection.values()) {
      String label = justify.getValue();
      directions.add(
          "." + justify.toString()
              .toLowerCase() + "()",
          label.substring(0, 1)
              .toUpperCase()
              + label
                  .substring(1));
    }
    directions.selectIndex(0);
    getBoundComponent().add(mainLayout);
    mainLayout.add(directions, boxLayout);
  }

  private void selectDirection(ListSelectEvent ev) {
    boxLayout.setDirection(FlexDirection.fromValue(ev.getSelectedItem().getText()));
  }
}
