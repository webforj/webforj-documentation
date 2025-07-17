package com.webforj.samples.views.infinitescroll;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.infinitescroll.InfiniteScroll;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Route("infinitescrollloading")
@FrameTitle("Custom Loading Indicator")
@InlineStyleSheet("context://css/infinitescroll/infinitescroll.css")
public class InfiniteScrollLoadingView extends Composite<Div> {

  public InfiniteScrollLoadingView() {
    Div self = getBoundComponent();
    self.setHeight("100vh");
    self.setStyle("overflow", "auto");

    AtomicInteger index = new AtomicInteger();

    Div canvas = new Div().setMaxWidth("600px")
        .addClassName("is-canvas");

    InfiniteScroll infiniteScroll = new InfiniteScroll("Fetching more records...");
    infiniteScroll.addClassName("is");
    infiniteScroll.setHeight("100%");
    infiniteScroll.add(canvas);

    Icon refreshIcon = TablerIcon.create("cloud-download");
    infiniteScroll.setIcon(refreshIcon);

    infiniteScroll.onScroll(e -> {
      if (index.get() > 40) {
        infiniteScroll.setCompleted(true);
        infiniteScroll.update();
        return;
      }

      for (int i = 0; i < 8; i++) {
        canvas.add(new Item());
      }

      index.addAndGet(8);
      infiniteScroll.update();
    });

    self.add(infiniteScroll);
  }

  public static class Item extends Composite<Div> {
    private final Random random = new Random();
    private final String[] names = {
        "John", "Jane", "Alice", "Bob", "Charlie", "Diana",
        "Ethan", "Fiona", "George", "Hannah", "Ian", "Jill"
    };

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
