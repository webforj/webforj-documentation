package com.webforj.samples.views.refresher;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.refresher.Refresher;
import com.webforj.router.annotation.Route;

@Route("refresherthemes")
@StyleSheet("ws://css/refresher/refresher.css")
public class RefresherThemesView extends Composite<Div> {

  private final List<Theme> themes = List.of(
    Theme.PRIMARY, Theme.SUCCESS, Theme.WARNING,
    Theme.DANGER, Theme.INFO, Theme.GRAY, Theme.DANGER
  );

  private final AtomicInteger themeIndex = new AtomicInteger(0);
  private final Random random = new Random();
  private final String[] names = {
    "John", "Jane", "Alice", "Bob", "Charlie", "Diana",
    "Ethan", "Fiona", "George", "Hannah", "Ian", "Jill"
  };

  public RefresherThemesView() {
    Div self = getBoundComponent();

    Div canvas = new Div().addClassName("is-canvas");
    canvas.setAttribute("theme", Theme.INFO.name().toLowerCase());
    self.add(canvas);

    Refresher refresher = new Refresher()
        .setTheme(Theme.INFO);

    refresher.onRefresh(e -> {
      canvas.removeAll();
      for (int i = 0; i < 8; i++) {
        canvas.add(new Item());
      }

      int next = themeIndex.updateAndGet(i -> (i + 1) % themes.size());
      Theme nextTheme = themes.get(next);
      refresher.setTheme(nextTheme);
      canvas.setAttribute("theme", nextTheme.name().toLowerCase());
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
