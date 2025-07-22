package com.webforj.samples.views.drawer;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.list.ComboBox;
import com.webforj.component.Composite;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.drawer.Drawer.Placement;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/drawer/drawerDemo.css")
@Route("drawerplacement")
@FrameTitle("Drawer Placement")
public class DrawerPlacementView extends Composite<FlexLayout> {

  Drawer drawer = new Drawer();
  ComboBox placements = new ComboBox();

  public DrawerPlacementView() {
    getBoundComponent().add(drawer);
    drawer.add(placements);
    drawer.open();
    drawer.addClassName("drawer");

    for (Placement placement : Drawer.Placement.values()) {
      placements.add(placement,
          placement.toString().substring(0, 1).toUpperCase() + placement.toString().substring(1).toLowerCase());
    }
    placements.selectIndex(4);
    placements.onSelect(e -> drawer.setPlacement((Placement) placements.getSelectedItem().getKey()));
  }
}
