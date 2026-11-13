---
title: "Persist resized Table column widths"
description: "Save user-resized Table column widths in LocalStorage and restore them on the next visit."
tags: [table]
components: [Table]
difficulty: intermediate
---

Give each column a stable ID, restore any saved width when the column is created, and save the new width from the `Table` column-resize event. Prefix the storage keys so widths from different tables don't collide.

```java
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.Route;
import com.webforj.webstorage.LocalStorage;
import java.util.List;
import java.util.function.Function;

@Route("persistent-orders-table")
public class PersistentOrdersTableView extends Composite<Div> {
  private static final String WIDTH_KEY_PREFIX = "orders-table.width.";

  private final Div self = getBoundComponent();
  private final LocalStorage storage = LocalStorage.getCurrent();
  private final Table<Order> table = new Table<>();

  public PersistentOrdersTableView() {
    addPersistentColumn("number", Order::number).setLabel("Order");
    addPersistentColumn("customer", Order::customer).setLabel("Customer");
    addPersistentColumn("status", Order::status).setLabel("Status");

    table.setItems(List.of(
        new Order(1042, "Northwind", "Ready"),
        new Order(1043, "Contoso", "Processing")));
    table.setSize("100%", "400px");

    table.onColumnResize(event -> {
      Column<?, ?> column = event.getColumn();
      storage.add(widthKey(column.getId()), Float.toString(event.getNewWidth()));
    });

    self.add(table);
  }

  private <V> Column<Order, V> addPersistentColumn(
      String id, Function<Order, V> valueProvider) {
    Column<Order, V> column = table.addColumn(id, valueProvider).setResizable(true);
    String savedWidth = storage.get(widthKey(id));

    if (savedWidth != null && !savedWidth.isBlank()) {
      try {
        column.setWidth(Float.parseFloat(savedWidth));
      } catch (NumberFormatException ignored) {
        storage.remove(widthKey(id));
      }
    }

    return column;
  }

  private static String widthKey(String columnId) {
    return WIDTH_KEY_PREFIX + columnId;
  }

  public record Order(long number, String customer, String status) {}
}
```

`LocalStorage` is scoped to the browser and origin. If multiple tables or user profiles share that origin, include a table and profile identifier in the key prefix.
