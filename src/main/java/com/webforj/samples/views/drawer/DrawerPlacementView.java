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
  private FlexLayout self = getBoundComponent();
  private Drawer drawer = new Drawer();

  public DrawerPlacementView() {
    self.setMargin("var(--dwc-space-l)");

    drawer.setLabel("Drawer Placement Options");

    RadioButton topOption = new RadioButton("Top");
    RadioButton topCenterOption = new RadioButton("Top Center");
    RadioButton bottomOption = new RadioButton("Bottom");
    RadioButton bottomCenterOption = new RadioButton("Bottom Center");
    RadioButton leftOption = new RadioButton("Left", true);
    RadioButton rightOption = new RadioButton("Right");

    topOption.setUserData("placement", Placement.TOP);
    topCenterOption.setUserData("placement", Placement.TOP_CENTER);
    bottomOption.setUserData("placement", Placement.BOTTOM);
    bottomCenterOption.setUserData("placement", Placement.BOTTOM_CENTER);
    leftOption.setUserData("placement", Placement.LEFT);
    rightOption.setUserData("placement", Placement.RIGHT);

    RadioButtonGroup placementGroup = new RadioButtonGroup("Placement Options", 
        topOption, topCenterOption, bottomOption, bottomCenterOption, leftOption, rightOption);

    FlexLayout groupLayout = new FlexLayout()
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-s)");
    groupLayout.add(placementGroup);

    placementGroup.onChange(e -> {
      RadioButton selected = e.getChecked();
      if (selected != null) {
        drawer.setPlacement((Drawer.Placement) selected.getUserData("placement"));
      }
    });

    drawer.add(groupLayout);
    drawer.setStyle("--dwc-drawer-max-width", "fit-content");

    Button openDrawer = new Button("Open Placement");
    openDrawer.onClick(e -> drawer.open());

    self.add(openDrawer, drawer);
    drawer.open();
  }
}