package com.webforj.samples.views.composite;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.Theme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.field.NumberField;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.H2;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.toast.Toast;
import com.webforj.dispatcher.EventDispatcher;
import com.webforj.dispatcher.EventListener;
import com.webforj.dispatcher.ListenerRegistration;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;
import java.util.EventObject;

@Route
@FrameTitle("Order Desk")
@BundleEntry("composite/ordercustomevent.css")
public class CompositeCustomEventView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final OrderForm form = new OrderForm();
  private final FlexLayout log = new FlexLayout();
  private final Paragraph empty = new Paragraph("No orders placed yet.");

  public CompositeCustomEventView() {
    form.onSubmit(this::recordOrder);

    empty.addClassName("orderdesk-empty");
    log.setDirection(FlexDirection.COLUMN).addClassName("orderdesk-log");
    log.add(empty);

    H3 logHeading = new H3("Placed orders");
    logHeading.addClassName("orderdesk-subhead");

    FlexLayout card = new FlexLayout(new H2("Order desk"), form, logHeading, log);
    card.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-l)")
        .addClassName("orderdesk-card");

    self.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .addClassName("orderdesk-container");
    self.setStyle("max-height", "100vh");
    self.setStyle("overflow-y", "auto");
    self.add(card);
  }

  private void recordOrder(OrderForm.OrderSubmittedEvent event) {
    empty.setVisible(false);

    Span id = new Span(event.getOrderId());
    id.addClassName("orderdesk-order-id");
    Span customer = new Span(event.getCustomer());
    Span total = new Span("$" + String.format("%.2f", event.getTotal()));
    total.addClassName("orderdesk-order-total");

    FlexLayout row = new FlexLayout(id, customer, total);
    row.setAlignment(FlexAlignment.CENTER).addClassName("orderdesk-order");
    log.add(row);
  }

  public static class OrderForm extends Composite<FlexLayout> {
    private static final double UNIT_PRICE = 4.75;

    private final FlexLayout self = getBoundComponent();
    private final EventDispatcher dispatcher = new EventDispatcher();
    private final TextField customer = new TextField("Customer name");
    private final NumberField quantity = new NumberField("Quantity");
    private final Button submit = new Button("Place order", ButtonTheme.PRIMARY);
    private int counter = 1000;

    public OrderForm() {
      quantity.setMin(1d).setMax(99d).setStep(1d).setValue(1d);

      self.setDirection(FlexDirection.COLUMN).setSpacing("var(--dwc-space-m)");
      self.add(customer, quantity, submit);

      submit.onClick(event -> submitOrder());
    }

    public ListenerRegistration<OrderSubmittedEvent> onSubmit(
        EventListener<OrderSubmittedEvent> listener) {
      return dispatcher.addListener(OrderSubmittedEvent.class, listener);
    }

    private void submitOrder() {
      String name = customer.getValue();
      if (name == null || name.isBlank()) {
        Toast.show("Enter a customer name", Theme.DANGER);
        return;
      }

      Double value = quantity.getValue();
      if (value == null || value < 1 || value > 99 || value % 1 != 0) {
        Toast.show("Enter a whole quantity between 1 and 99", Theme.DANGER);
        return;
      }

      int count = value.intValue();
      dispatcher.dispatchEvent(
          new OrderSubmittedEvent(this, "#" + (++counter), name, count * UNIT_PRICE));

      customer.setValue("");
    }

    public static class OrderSubmittedEvent extends EventObject {
      private final String orderId;
      private final String customer;
      private final double total;

      public OrderSubmittedEvent(Object source, String orderId, String customer, double total) {
        super(source);
        this.orderId = orderId;
        this.customer = customer;
        this.total = total;
      }

      public String getOrderId() {
        return orderId;
      }

      public String getCustomer() {
        return customer;
      }

      public double getTotal() {
        return total;
      }
    }
  }
}
