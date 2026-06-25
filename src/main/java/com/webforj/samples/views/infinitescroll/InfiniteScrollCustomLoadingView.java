package com.webforj.samples.views.infinitescroll;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.infinitescroll.InfiniteScroll;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Route
@FrameTitle("Fully Customized Loading")
@StyleSheet("ws://css/infinitescroll/infinitescrollcustom.css")
public class InfiniteScrollCustomLoadingView extends Composite<Div> {
  private final Div self = getBoundComponent();

  // Shared names list for all Item instances
  private static final List<String> NAMES =
      List.of(
          "John", "Jane", "Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona", "George", "Hannah",
          "Ian", "Jill");

  public InfiniteScrollCustomLoadingView() {
    self.setHeight("100vh").setStyle("overflow", "auto");

    Div canvas = new Div().setMaxWidth("600px").addClassName("is-canvas");

    InfiniteScroll infiniteScroll = new InfiniteScroll().addClassName("is").setHeight("100%");
    infiniteScroll.add(canvas);

    Div customLoadingContent = new Div().addClassName("custom-loading");

    Icon cloudIcon =
        FeatherIcon.CLOUD.create().setSize("32px", "32px").addClassName("loading-icon");

    Span loadingText = new Span("Loading awesome content...");
    customLoadingContent.add(cloudIcon, loadingText);

    infiniteScroll.addToContent(customLoadingContent);

    AtomicInteger index = new AtomicInteger();
    infiniteScroll.onScroll(e -> handleScroll(infiniteScroll, canvas, index));

    self.add(infiniteScroll);
  }

  /** Handles scroll events by adding new items to the canvas. */
  private void handleScroll(InfiniteScroll infiniteScroll, Div canvas, AtomicInteger index) {
    if (index.get() > 40) {
      infiniteScroll.setCompleted(true);
      infiniteScroll.update();
      return;
    }

    for (int i = 0; i < 8; i++) {
      canvas.add(new Item(NAMES));
    }

    index.addAndGet(8);
    infiniteScroll.update();
  }

  /** Item component representing a single row in the infinite scroll. */
  public static class Item extends Composite<Div> {
    private final Random random = new Random();
    private final List<String> names;

    public Item(List<String> names) {
      this.names = names;
      initialize();
    }

    private void initialize() {
      Div self = getBoundComponent();

      // Select random name from provided list
      String name = names.get(random.nextInt(names.size()));

      Div nameDiv = new Div(name).addClassName("item-name");
      Div excerpt =
          new Div(
                  """
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. \
              Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.""")
              .addClassName("item-excerpt");

      Icon arrowIcon = FeatherIcon.ARROW_RIGHT.create().setMinSize("24px", "24px");

      self.add(
          FlexLayout.create(FlexLayout.create(nameDiv, excerpt).vertical().build(), arrowIcon)
              .horizontal()
              .justify()
              .between()
              .align()
              .center()
              .build());
    }
  }
}
