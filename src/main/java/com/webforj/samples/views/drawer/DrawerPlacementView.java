package com.webforj.samples.views.drawer;

import com.webforj.component.Composite;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.drawer.Drawer.Placement;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.RadioButton;
import com.webforj.component.optioninput.RadioButtonGroup;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Drawer Placement")
public class DrawerPlacementView extends Composite<FlexLayout> {

  FlexLayout layout = getBoundComponent();
  Drawer drawer = new Drawer();

  public DrawerPlacementView() {
    drawer.setLabel("Drawer Placement Options");
    drawer.addClassName("drawer");
    drawer.open();

    RadioButton topOption = new RadioButton("Top");
    RadioButton bottomOption = new RadioButton("Bottom");
    RadioButton leftOption = new RadioButton("Left", true);
    RadioButton rightOption = new RadioButton("Right");

    RadioButtonGroup placementGroup = new RadioButtonGroup("Placement Options", topOption, bottomOption, leftOption, rightOption);

    FlexLayout groupLayout = new FlexLayout();
    groupLayout.setDirection(FlexDirection.COLUMN);
    groupLayout.setSpacing("var(--dwc-space-s)");
    groupLayout.add(placementGroup, topOption, bottomOption, leftOption, rightOption);

    placementGroup.onValueChange(event -> {
      RadioButton selected = placementGroup.getChecked();
      if (selected != null) {
        switch (selected.getText()) {
          case "Top":
            drawer.setPlacement(Placement.TOP);
            break;
          case "Bottom":
            drawer.setPlacement(Placement.BOTTOM);
            break;
          case "Right":
            drawer.setPlacement(Placement.RIGHT);
            break;
          default:
            drawer.setPlacement(Placement.LEFT);
            break;
        }
      }
    });

    drawer.add(groupLayout);
    layout.add(drawer);
  }
}