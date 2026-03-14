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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Route
@FrameTitle("Drawer Placement")
public class DrawerPlacementView extends Composite<FlexLayout> {
  // self field enables fluent method chaining from the bound component
  private final FlexLayout self = getBoundComponent();
  private final Drawer drawer = new Drawer();

  public DrawerPlacementView() {
    self.setMargin("var(--dwc-space-l)");

    drawer.setLabel("Drawer Placement Options");

    List<RadioButton> radioButtons = new ArrayList<>();

    for (Placement placement: Placement.values()) {
      // Turn Enum value name into capitalized words
      String text = Arrays.stream(placement.name().split("_"))
              .map(s -> s.charAt(0) + s.substring(1).toLowerCase())
              .collect(Collectors.joining(" "));
      boolean checked = placement == Placement.LEFT;
      RadioButton button = new RadioButton(text, checked);
      button.setUserData("placement", placement);
      radioButtons.add(button);
    }

    RadioButtonGroup placementGroup = new RadioButtonGroup("Placement Options", 
        radioButtons.toArray(RadioButton[]::new));

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