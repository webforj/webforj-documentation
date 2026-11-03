package com.webforj.samples.verify;

import com.webforj.component.Composite;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.field.TextField;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column.PinDirection;
import com.webforj.component.table.Column.SortDirection;
import com.webforj.component.table.Table;
import com.webforj.component.table.renderer.BadgeRenderer;
import com.webforj.component.table.renderer.ConditionalRenderer;
import com.webforj.component.table.renderer.MaskedNumberRenderer;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.data.repository.DelegatingRepository;
import com.webforj.data.repository.OrderCriteria;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/** Verification of the snippets used in the java-data-grid blog post. */
public class VerifyBlogJavaDataGridSnippets extends Composite<Div> {

  public VerifyBlogJavaDataGridSnippets() {
    getBoundComponent().add(new OrdersGrid(), new RemoteOrdersGrid());
  }

  public record Order(
      long id, String customer, String region, String status, double total, LocalDate placed) {}

  // Snippet 1 and 2
  public static class OrdersGrid extends Composite<Div> {
    private String search = "";

    public OrdersGrid() {
      List<Order> orders = new ArrayList<>();
      orders.add(new Order(1001, "Northwind Traders", "EMEA", "Paid", 1840.50, LocalDate.now()));
      orders.add(new Order(1002, "Contoso Ltd", "APAC", "Pending", 920.00, LocalDate.now()));

      CollectionRepository<Order> repository = new CollectionRepository<>(orders);
      repository.setKeyProvider(Order::id);

      Table<Order> table = new Table<>();
      table.setHeight("480px");
      table.setStriped(true);
      table.setRepository(repository);
      table.setSelectionMode(Table.SelectionMode.MULTIPLE);
      table.setMultiSorting(true);

      table
          .addColumn("id", Order::id)
          .setLabel("Order")
          .setSortable(true)
          .setWidth(100f)
          .setPinDirection(PinDirection.LEFT);
      table
          .addColumn("customer", Order::customer)
          .setLabel("Customer")
          .setSortable(true)
          .setFlex(2f);
      table.addColumn("region", Order::region).setLabel("Region").setSortable(true).setFlex(1f);
      table
          .addColumn("status", Order::status)
          .setLabel("Status")
          .setSortable(true)
          .setRenderer(
              new ConditionalRenderer<Order>()
                  .when("Paid", new BadgeRenderer<Order>(BadgeTheme.SUCCESS))
                  .when("Pending", new BadgeRenderer<Order>(BadgeTheme.WARNING))
                  .otherwise(new BadgeRenderer<>(BadgeTheme.DEFAULT)));
      table
          .addColumn("total", Order::total)
          .setLabel("Total")
          .setSortable(true)
          .setSortDirection(SortDirection.DESC)
          .setRenderer(new MaskedNumberRenderer<>("$###,##0.00", Locale.US));

      table.onItemSelectionChange(
          e -> {
            List<Order> selected = e.getSelectedItems();
            double sum = selected.stream().mapToDouble(Order::total).sum();
            System.out.println(selected.size() + " orders selected, total " + sum);
          });

      TextField searchField = new TextField(TextField.Type.SEARCH, "Search customers");
      searchField.onModify(
          e -> {
            search = e.getText().toLowerCase();
            repository.setBaseFilter(o -> o.customer().toLowerCase().contains(search));
            repository.commit();
          });

      // Editing: change the data, then commit the one entity
      Order updated = new Order(1002, "Contoso Ltd", "APAC", "Paid", 920.00, LocalDate.now());
      orders.set(1, updated);
      repository.commit(updated);

      getBoundComponent().add(searchField, table);
    }
  }

  // Snippet 3
  public static class OrderQuery {
    private String status;
    private String customerContains;

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getCustomerContains() {
      return customerContains;
    }

    public void setCustomerContains(String customerContains) {
      this.customerContains = customerContains;
    }
  }

  public interface OrderService {
    List<Order> find(
        int offset, int limit, OrderQuery query, String sortProperty, boolean descending);

    int count(OrderQuery query);

    Optional<Order> findById(Object id);
  }

  public static class RemoteOrdersGrid extends Composite<Div> {
    private OrderService orderService;

    public RemoteOrdersGrid() {
      DelegatingRepository<Order, OrderQuery> repository =
          new DelegatingRepository<>(
              criteria -> {
                String sortProperty = null;
                boolean descending = false;
                for (OrderCriteria<Order, ?> order : criteria.getOrderCriteria()) {
                  sortProperty = order.getPropertyName();
                  descending = order.getDirection() == OrderCriteria.Direction.DESC;
                  break;
                }

                return orderService
                    .find(
                        criteria.getOffset(),
                        criteria.getLimit(),
                        criteria.getFilter(),
                        sortProperty,
                        descending)
                    .stream();
              },
              criteria -> orderService.count(criteria.getFilter()),
              id -> orderService.findById(id));

      OrderQuery query = new OrderQuery();
      query.setStatus("Pending");
      repository.setBaseFilter(query);

      Table<Order> table = new Table<>();
      table.setRepository(repository);
      table.setOverscan(10);
      table.addColumn("customer", Order::customer).setSortable(true).setPropertyName("customer");
      table.addColumn("total", Order::total).setSortable(true).setPropertyName("total");

      getBoundComponent().add(table);
    }
  }

  // Snippet 4 requires Spring Data JPA on the classpath:
  //
  // import com.webforj.data.repository.spring.SpringDataRepository;
  // import org.springframework.data.jpa.domain.Specification;
  //
  // SpringDataRepository<OrderEntity, Long> repository = new
  // SpringDataRepository<>(orderRepository);
  // Table<OrderEntity> table = new Table<>();
  // table.setRepository(repository);
  // table.addColumn("customer", OrderEntity::getCustomer).setSortable(true);
  // table.addColumn("total", OrderEntity::getTotal).setSortable(true);
  //
  // Specification<OrderEntity> pending = (root, query, cb) -> cb.equal(root.get("status"),
  // "Pending");
  // repository.setBaseFilter(pending);
}
