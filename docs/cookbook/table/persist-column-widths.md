---
title: "Save and restore Table column widths between sessions"
description: "Persist per-column widths to LocalStorage on resize and restore them the next time the view loads."
tags: [table]
components: [Table]
difficulty: intermediate
---

Mark each column as resizable, read saved widths from `LocalStorage` on startup, and write updated widths back inside `onColumnResize`.

```java
LocalStorage storage = LocalStorage.getCurrent();
String storageKeyPrefix = "products-table.column-width.";

Table<Product> table = new Table<>();
Column<Product, String> name = table.addColumn("name", Product::getName).setResizable(true);
Column<Product, Number> price = table.addColumn("price", Product::getPrice).setResizable(true);

restoreWidth(name, storage, storageKeyPrefix);
restoreWidth(price, storage, storageKeyPrefix);

table.onColumnResize(e -> {
  String key = storageKeyPrefix + e.getColumn().getId();
  storage.add(key, String.valueOf(e.getNewWidth()));
});

void restoreWidth(Column<Product, ?> column, LocalStorage storage, String prefix) {
  String saved = storage.get(prefix + column.getId());
  if (saved != null) {
    column.setWidth(Float.parseFloat(saved));
  }
}
```
