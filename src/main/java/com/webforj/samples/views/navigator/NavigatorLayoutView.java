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
  private final Div self = getBoundComponent();
  // UI Components - initialized at field declaration for layout creation
  private final Navigator nav = new Navigator(100);
  private final ChoiceBox navLayout = new ChoiceBox();
  private final FlexLayout layout = FlexLayout.create(navLayout, nav).vertical().build();

  public NavigatorLayoutView() {
    // Configure navigator and layout
    layout.setMaxWidth("400px");
    nav.addClassName("nav")
            .getPaginator().setMax(5);

    // Configure layout selector
    navLayout.insert("NONE", "PAGES", "PREVIEW", "QUICK_JUMP");
    navLayout.selectIndex(2);

    // Handle layout selection changes
    navLayout.onSelect(ev -> {
      String selected = ev.getSelectedItem().getText();
      nav.setLayout(switch (selected) {
        case "NONE" -> Layout.NONE;
        case "PAGES" -> Layout.PAGES;
        case "PREVIEW" -> Layout.PREVIEW;
        case "QUICK_JUMP" -> Layout.QUICK_JUMP;
        default -> nav.getLayout();
      });
    });

    // Add layout to container
    self.setStyle("padding", "20px")
        .add(layout);
  }
}
