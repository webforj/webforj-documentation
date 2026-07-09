package com.webforj.samples.views.composite;

import com.webforj.Interval;
import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.List;
import java.util.Random;

@Route
@FrameTitle("Order Queue")
@BundleEntry("composite/liveintervalpanel.css")
public class CompositeIntervalView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public CompositeIntervalView() {
    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .addClassName("queue-container");
    self.setStyle("max-height", "100vh");
    self.setStyle("overflow-y", "auto");
    self.add(new OrderQueue());
  }

  public static class OrderQueue extends Composite<FlexLayout> {
    private static final int MAX_VISIBLE = 6;
    private static final List<String> CUSTOMERS =
        List.of("Ava", "Liam", "Noah", "Emma", "Olivia", "Mateo", "Sophia", "Lucas");
    private static final List<String> ITEMS =
        List.of("Latte", "Cold brew", "Cappuccino", "Espresso", "Mocha", "Flat white");

    private final FlexLayout self = getBoundComponent();
    private final FlexLayout list = new FlexLayout();
    private final Button toggle = new Button("Start", ButtonTheme.OUTLINED_GRAY);
    private final Random random = new Random();
    private final Interval interval = new Interval(2.5f, event -> addOrder());

    private int counter = 1040;

    public OrderQueue() {
      FlexLayout header = new FlexLayout(new H2("Incoming orders"), toggle);
      header
          .setAlignment(FlexAlignment.CENTER)
          .setJustifyContent(FlexJustifyContent.BETWEEN)
          .addClassName("queue-header");

      list.setDirection(FlexDirection.COLUMN).addClassName("queue-list");

      self.setDirection(FlexDirection.COLUMN)
          .setSpacing("var(--dwc-space-m)")
          .addClassName("queue-card");
      self.add(header, list);

      for (int i = 0; i < 3; i++) {
        addOrder();
      }

      toggle.onClick(event -> togglePolling());
    }

    @Override
    protected void onDidDestroy() {
      interval.stop();
    }

    private void togglePolling() {
      if (interval.isRunning()) {
        interval.stop();
        toggle.setText("Start");
      } else {
        interval.start();
        toggle.setText("Pause");
      }
    }

    private void addOrder() {
      String customer = CUSTOMERS.get(random.nextInt(CUSTOMERS.size()));
      String item = ITEMS.get(random.nextInt(ITEMS.size()));
      int cups = 1 + random.nextInt(3);

      Span id = new Span("#" + (++counter));
      id.addClassName("queue-order-id");
      Span who = new Span(customer);
      Span what = new Span(cups + "x " + item);
      what.addClassName("queue-order-item");

      FlexLayout row = new FlexLayout(id, who, what);
      row.setAlignment(FlexAlignment.CENTER)
          .setSpacing("var(--dwc-space-m)")
          .addClassName("queue-order");
      list.add(row);

      if (list.getComponentCount() > MAX_VISIBLE) {
        list.remove(list.getComponents().get(0));
      }
    }
  }
}
