package com.webforj.samples.views.applayout;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Img;

public class DrawerLogo extends Composite<Div> {
  private final Div self = getBoundComponent();

  public DrawerLogo() {
    self.addClassName("drawer__logo");
    self.add(new Img("ws://img/webforj_icon.svg", "logo"));
  }
}
