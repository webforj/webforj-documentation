---
title: "Save and restore Table column widths between sessions"
description: "Persist per-column widths to LocalStorage on resize and restore them the next time the view loads."
tags: [table]
components: [Table]
difficulty: intermediate
---

Use explicit fixed-width columns, restore each column's saved width on startup, and write the resized column's new width from `onColumnResize`. Fixed widths keep neighboring columns from becoming hidden, implicit state. If your table uses flex or auto-fit sizing, persist that sizing model separately instead of saving only pixel widths.

```java
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.router.annotation.Route;
import com.webforj.webstorage.LocalStorage;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Route("products")
public class ProductsView extends Composite<Div> {
  private final Div self = getBoundComponent();
  private final LocalStorage storage = LocalStorage.getCurrent();
  private final String storageKeyPrefix = "products-table.column-width.";
  private final Table<Product> table = new Table<>();
  private final Map<String, Column<Product, ?>> columns = new LinkedHashMap<>();

  public ProductsView() {
    addColumn("name", table.addColumn("name", Product::getName)
        .setLabel("Name")
        .setWidth(220f)
        .setFlex(0f)
        .setResizable(true));

    addColumn("price", table.addColumn("price", Product::getPrice)
        .setLabel("Price")
        .setWidth(120f)
        .setFlex(0f)
        .setResizable(true));

    columns.forEach(this::restoreWidth);

    table.onColumnResize(e -> {
      String columnId = e.getColumn().getId();
      Column<Product, ?> column = columns.get(columnId);
      if (column != null) {
        column.setWidth(e.getNewWidth());
        storage.add(storageKey(columnId), Float.toString(e.getNewWidth()));
      }
    });

    table.setRepository(new CollectionRepository<>(List.of(
        new Product("Keyboard", 79.99),
        new Product("Dock", 149.99))));

    table.setSize("100%", "50vh");
    table.refreshColumns();
    self.add(table);
  }

  private void addColumn(String id, Column<Product, ?> column) {
    columns.put(id, column);
  }

  private void restoreWidth(String id, Column<Product, ?> column) {
    String saved = storage.get(storageKey(id));
    if (saved == null) {
      return;
    }

    try {
      column.setWidth(Float.parseFloat(saved));
    } catch (NumberFormatException ex) {
      storage.remove(storageKey(id));
    }
  }

  private String storageKey(String columnId) {
    return storageKeyPrefix + columnId;
  }

  public static class Product {
    private final String name;
    private final Double price;

    public Product(String name, Double price) {
      this.name = name;
      this.price = price;
    }

    public String getName() {
      return name;
    }

    public Double getPrice() {
      return price;
    }
  }
}
```
