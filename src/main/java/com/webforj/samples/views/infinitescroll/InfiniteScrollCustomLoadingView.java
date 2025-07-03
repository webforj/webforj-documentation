package com.webforj.samples.views.infinitescroll;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.icons.Icon;
import com.webforj.component.infinitescroll.InfiniteScroll;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import com.webforj.samples.config.RouteConfig;


@Route(RouteConfig.INFINITE_SCROLL_CUSTOM_LOADING)
@FrameTitle("Fully Customized Loading")
@InlineStyleSheet("context://css/infinitescroll/infinitescrollcustom.css")
public class InfiniteScrollCustomLoadingView extends Composite<Div> {

  public InfiniteScrollCustomLoadingView() {
    Div self = getBoundComponent();
    self.setHeight("100vh");
    self.setStyle("overflow", "auto");

    AtomicInteger index = new AtomicInteger();

    Div canvas = new Div().setMaxWidth("600px")
        .addClassName("is-canvas");

    InfiniteScroll infiniteScroll = new InfiniteScroll();
    infiniteScroll.addClassName("is");
    infiniteScroll.setHeight("100%");
    infiniteScroll.add(canvas);

    Div customLoadingContent = new Div();
    customLoadingContent.addClassName("custom-loading");

    Icon cloudIcon = FeatherIcon.CLOUD.create();
    cloudIcon.setSize("32px", "32px");
    cloudIcon.addClassName("loading-icon");

    Span loadingText = new Span("Loading awesome content...");
    customLoadingContent.add(cloudIcon, loadingText);

    infiniteScroll.addToContent(customLoadingContent);

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

      Icon arrowIcon = FeatherIcon.ARROW_RIGHT.create();
      arrowIcon.setMinSize("24px", "24px");

      self.add(FlexLayout.create(
          FlexLayout.create(nameDiv, excerpt).vertical().build(),
          arrowIcon)
          .horizontal()
          .justify().between()
          .align().center()
          .build());
    }
  }
}
