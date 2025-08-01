package com.webforj.samples.views.refresher;

import java.util.Random;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.refresher.Refresher;
import com.webforj.router.annotation.Route;

@Route
@StyleSheet("ws://css/refresher/refresher.css")
public class RefresherView extends Composite<Div> {

  private final Random random = new Random();
  private final String[] names = {
    "John", "Jane", "Alice", "Bob", "Charlie", "Diana",
    "Ethan", "Fiona", "George", "Hannah", "Ian", "Jill"
  };

  public RefresherView() {
    Div self = getBoundComponent();

    Div canvas = new Div().addClassName("is-canvas");
    self.add(canvas);

    Refresher refresher = new Refresher();

    refresher.onRefresh(e -> {
      canvas.removeAll();
      for (int i = 0; i < 8; i++) {
        canvas.add(new Item());
      }
      refresher.finish();
    });

    self.add(refresher);

    for (int i = 0; i < 8; i++) {
      canvas.add(new Item());
    }
  }

  class Item extends Composite<Div> {
    public Item() {
      Div self = getBoundComponent();

      String name = names[random.nextInt(names.length)];

      Div nameDiv = new Div(name).addClassName("item-name");
      Div excerpt = new Div("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
          + "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
          .addClassName("item-excerpt");

      Icon icon = FeatherIcon.ARROW_RIGHT.create();
      icon.setMinSize("24px", "24px");

      self.add(FlexLayout.create(FlexLayout.create(nameDiv, excerpt).vertical().build(), icon)
          .horizontal()
          .justify().between()
          .align().center()
          .build());
    }
  }
}
