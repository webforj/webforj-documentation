package com.webforj.samples.views.drawer;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
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
    layout.setMargin("var(--dwc-space-l)");

    drawer.setLabel("Drawer Placement Options");

    RadioButton topOption = new RadioButton("Top");
    RadioButton topCenterOption = new RadioButton("Top Center");
    RadioButton bottomOption = new RadioButton("Bottom");
    RadioButton bottomCenterOption = new RadioButton("Bottom Center");
    RadioButton leftOption = new RadioButton("Left", true);
    RadioButton rightOption = new RadioButton("Right");

    RadioButtonGroup placementGroup = new RadioButtonGroup("Placement Options", 
        topOption, topCenterOption, bottomOption, bottomCenterOption, leftOption, rightOption);

    FlexLayout groupLayout = new FlexLayout();
    groupLayout.setDirection(FlexDirection.COLUMN)
            .setSpacing("var(--dwc-space-s)")
            .add(placementGroup, topOption, topCenterOption, bottomOption, bottomCenterOption, 
                leftOption, rightOption);

    placementGroup.onValueChange(event -> {
      RadioButton selected = placementGroup.getChecked();
      if (selected != null) {
        switch (selected.getText()) {
          case "Top":
            drawer.setPlacement(Placement.TOP);
            break;
          case "Top Center":
            drawer.setPlacement(Placement.TOP_CENTER);
            break;
          case "Bottom":
            drawer.setPlacement(Placement.BOTTOM);
            break;
          case "Bottom Center":
            drawer.setPlacement(Placement.BOTTOM_CENTER);
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
    drawer.setStyle("--dwc-drawer-max-width", "fit-content");

    Button openDrawer = new Button("Open Placement");
    openDrawer.onClick(e -> drawer.open());

    layout.add(openDrawer, drawer);    
    drawer.open();
  }
}