package com.webforj.samples.views.drawer;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("context://css/drawer/drawerDemo.css")
@Route
@FrameTitle("Drawer Auto-Focus")
public class DrawerAutoFocusView extends Composite<FlexLayout> {

  Drawer drawer = new Drawer();
  CheckBox checkbox = new CheckBox("Checkbox auto-focused");

  public DrawerAutoFocusView() {

    getBoundComponent().add(drawer);
    drawer.open();
    drawer.add(checkbox);
    drawer.addClassName("drawer");
    drawer.setAutoFocus(true);
  }
}
