package com.webforj.samples.views.navigator;

import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.list.ChoiceBox;
import com.webforj.component.navigator.Navigator;
import com.webforj.component.navigator.Navigator.Layout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Navigator Layout")
public class NavigatorLayoutView extends Composite<Div> {
  private Navigator nav = new Navigator(100);
  private ChoiceBox navLayout = new ChoiceBox();
  private FlexLayout layout = FlexLayout.create(navLayout, nav).vertical().build();

  public NavigatorLayoutView() {
    
    layout.setMaxWidth("400px");
    nav.getPaginator().setMax(5);
    nav.addClassName("nav");
    
    navLayout.insert("NONE", "PAGES", "PREVIEW", "QUICK_JUMP");
    navLayout.selectIndex(2);
    navLayout.onSelect(ev -> {
      String selected = ev.getSelectedItem().getText();
      switch (selected) {
        case "NONE":
        nav.setLayout(Layout.NONE);
        break;
        case "PAGES":
        nav.setLayout(Layout.PAGES);
        break;
        case "PREVIEW":
        nav.setLayout(Layout.PREVIEW);
        break;
        case "QUICK_JUMP":
        nav.setLayout(Layout.QUICK_JUMP);
        break;
        default:
        break;
      }
    });
    
    getBoundComponent().setStyle("padding", "20px");
    getBoundComponent().add(layout);
  }
}
