---
title: "Java Data Grid: Build Fast, Sortable Tables with webforJ"
description: "Build a Java data grid in webforJ with the Table component. Add columns, sorting, filtering, selection, renderers, and lazy loading for large data sets."
slug: java-data-grid
date: 2026-10-20
authors: webforJ
tags: [components, table, performance, renderers]
image: https://cdn.webforj.com/webforj-documentation/blogs/2026-10-20-java-data-grid/cover.jpg
hide_table_of_contents: false
---

![cover image](https://cdn.webforj.com/webforj-documentation/blogs/2026-10-20-java-data-grid/cover.jpg)

The webforJ `Table` is a Java data grid for the browser. You define columns with method references, hand it a repository, and it gives you sorting, selection, pinned columns, cell renderers, and virtual scrolling. All of it is configured in Java, with no JavaScript grid library to wire up.

This guide builds an orders grid from an in-memory list, then switches the same table to lazy loading from a database or REST API. If you need a Java table component for web apps that handles large data sets, the second half is the part to read.

<!-- truncate -->

## Create a Java data grid with the Table {#create-a-java-data-grid}

The example data is a list of customer orders. Each row has an id, customer, region, status, total, and order date.

```java
public record Order(
    long id, String customer, String region, String status, double total, LocalDate placed) {}
```

A `Table<Order>` needs two things: columns and a repository. The `CollectionRepository` wraps any Java collection, and `setKeyProvider` tells it how to identify a row, which matters later when you update a single order.

```java
import com.webforj.component.table.Column.PinDirection;
import com.webforj.component.table.Table;
import com.webforj.data.repository.CollectionRepository;

CollectionRepository<Order> repository = new CollectionRepository<>(orders);
repository.setKeyProvider(Order::id);

Table<Order> table = new Table<>();
table.setHeight("480px");
table.setStriped(true);
table.setRepository(repository);

table.addColumn("id", Order::id).setLabel("Order").setWidth(100f)
    .setPinDirection(PinDirection.LEFT);
table.addColumn("customer", Order::customer).setLabel("Customer").setFlex(2f);
table.addColumn("region", Order::region).setLabel("Region").setFlex(1f);
```

Each `addColumn` call takes a column id and a value provider. Width and flex are mutually exclusive: use `setWidth` for a fixed pixel size or `setFlex` to share the remaining space in proportion. See [Columns](/docs/components/table/columns) for min and max widths, auto-sizing, and visibility.

## Add sorting, selection, and renderers {#sorting-selection-and-renderers}

Columns aren't sortable until you say so. Turn it on per column, set an initial direction, and allow multi-column sorting if users need it.

Selection has three modes: `SINGLE`, `MULTIPLE`, and `NONE`. The selection event hands you the selected items directly.

Renderers change how a cell looks without changing the data. `MaskedNumberRenderer` formats the total as currency, and `ConditionalRenderer` picks a badge per status value.

```java
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.table.Column.SortDirection;
import com.webforj.component.table.renderer.BadgeRenderer;
import com.webforj.component.table.renderer.ConditionalRenderer;
import com.webforj.component.table.renderer.MaskedNumberRenderer;
import java.util.Locale;

table.setSelectionMode(Table.SelectionMode.MULTIPLE);
table.setMultiSorting(true);

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

table.onItemSelectionChange(e -> {
  double sum = e.getSelectedItems().stream().mapToDouble(Order::total).sum();
  System.out.println(e.getSelectedItems().size() + " orders selected, total " + sum);
});
```

Renderers run in the browser, so the server sends raw values and the client handles presentation. Use a renderer only when you need visual output such as badges, icons, or buttons. If you just need to format a value, a value provider is cheaper. The [Rendering docs](/docs/components/table/rendering) list the built-in renderers and the `Condition` API, and [Making data display less boring in webforJ](/blog/table-renderers-custom-cells) walks through custom ones.

## Filter and edit rows {#filter-and-edit-rows}

Filtering belongs to the repository, not the table. Set a base filter predicate, then call `commit()` so connected components refresh.

```java
TextField searchField = new TextField(TextField.Type.SEARCH, "Search customers");
searchField.onModify(e -> {
  String term = e.getText().toLowerCase();
  repository.setBaseFilter(o -> o.customer().toLowerCase().contains(term));
  repository.commit();
});
```

Editing works the same way. Change the data, then commit the entity you changed:

```java
Order updated = new Order(1002, "Contoso Ltd", "APAC", "Paid", 920.00, LocalDate.now());
orders.set(1, updated);
repository.commit(updated);
```

`commit(entity)` finds the row by its key and updates only that row. `commit()` with no argument refreshes everything, so prefer the targeted call on large tables. The [Filtering](/docs/components/table/filtering) and [Editing and refreshing](/docs/components/table/refreshing) pages have runnable demos.

## How the Table handles large data {#large-data}

The `Table` uses virtual scrolling. It renders only the rows in the visible area and recycles those DOM elements as the user scrolls, so the number of rows in the dataset has little effect on how many elements exist in the page. The `setOverscan` method controls how many extra rows render outside the viewport. A higher value means smoother fast scrolling at the cost of more rendered rows.

Virtual scrolling solves the browser side. The server side is a separate question. If your repository holds a million records, you don't want to load them all into memory to show 30. That is where query-capable repositories come in.

## Lazy loading with a delegating repository {#lazy-loading}

A `DelegatingRepository` takes three functions: one that returns a page of results, one that returns the total count, and one that finds a row by key. The table calls them with a `RepositoryCriteria` carrying the offset, limit, sort order, and your filter object, so your data source only does the work for the rows on screen.

```java
import com.webforj.data.repository.DelegatingRepository;
import com.webforj.data.repository.OrderCriteria;

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
              .find(criteria.getOffset(), criteria.getLimit(), criteria.getFilter(),
                  sortProperty, descending)
              .stream();
        },
        criteria -> orderService.count(criteria.getFilter()),
        id -> orderService.findById(id));

OrderQuery query = new OrderQuery();
query.setStatus("Pending");
repository.setBaseFilter(query);

Table<Order> table = new Table<>();
table.setRepository(repository);
table.addColumn("customer", Order::customer).setSortable(true).setPropertyName("customer");
table.addColumn("total", Order::total).setSortable(true).setPropertyName("total");
```

`OrderQuery` is your own class, for example with `status` and `customerContains` fields that map to query parameters or `WHERE` clauses. The repository doesn't care what the filter type is, which is why the same table code works for a REST API, JDBC, or a search engine.

`setPropertyName` is the important line for sorting. When a user clicks a header, the property name travels in the `OrderCriteria`, and your function turns it into an `ORDER BY` or a query parameter. Without it, the table falls back to the column id, which silently breaks sorting if the id doesn't match a backend property. Server sorting is the default. Client sorting doesn't work when only part of the data is loaded, so a lazy table sorts on the server. The [Custom data sources](/docs/advanced/repository/delegating-repository) and [Querying data](/docs/advanced/repository/querying-data) pages cover the full criteria API.

For a complete REST example with Spring Boot, see [Loading data from REST APIs in webforJ](/blog/data-loading-strategies-rest-apis).

## Use Spring Data JPA directly {#spring-data}

If the orders live in a JPA database, you can skip writing the three functions. `SpringDataRepository` adapts a Spring Data repository to the webforJ repository interface and detects which Spring Data interfaces it implements, such as paging, sorting, and specifications.

```java
import com.webforj.data.repository.spring.SpringDataRepository;
import org.springframework.data.jpa.domain.Specification;

SpringDataRepository<OrderEntity, Long> repository = new SpringDataRepository<>(orderRepository);

Table<OrderEntity> table = new Table<>();
table.setRepository(repository);
table.addColumn("customer", OrderEntity::getCustomer).setSortable(true);
table.addColumn("total", OrderEntity::getTotal).setSortable(true);

Specification<OrderEntity> pending = (root, query, cb) -> cb.equal(root.get("status"), "Pending");
repository.setBaseFilter(pending);
```

For the best results, make your repository extend both `JpaRepository` and `JpaSpecificationExecutor`. Header sorting then reaches the database as an `ORDER BY`, and specifications handle filtering. The [Spring Data JPA integration page](/docs/integrations/spring/spring-data-jpa) shows the setup.

## Which repository should you pick? {#which-repository}

| Data source | Repository | Where sorting and filtering happen |
| --- | --- | --- |
| Small list already in memory | `CollectionRepository` | In the JVM, with a `Predicate` |
| REST API, JDBC, search engine | `DelegatingRepository` | In your data source, from the criteria |
| Spring Data JPA | `SpringDataRepository` | In the database, via paging, sorting, and `Specification` |

Start with `CollectionRepository` while the dataset is small. Moving to a delegating repository later mostly changes the repository line and the filter type. Your column definitions stay the same, apart from `setPropertyName` on sortable columns.

## Frequently asked questions {#faq}

### What is the best Java data grid for web apps?

The best choice is the one that fits your stack. The webforJ `Table` suits teams that want to build the whole UI in Java, because columns, renderers, sorting, and data loading are all configured with Java classes and lambdas.

### How does the webforJ Table handle large data sets?

It combines virtual scrolling in the browser with repository-based loading on the server. Only visible rows render, and a `DelegatingRepository` or `SpringDataRepository` fetches data by offset and limit instead of loading everything.

### Can users edit data directly in a Java data grid?

The `Table` edits through its repository. You open an editor, such as a dialog or a form, from a renderer button or a selection event, update the entity, and call `commit(entity)` to refresh that row.

### How do I sort a webforJ Table by a field that differs from the column id?

Call `setPropertyName` on the column with the backend property name. The name is passed in the `OrderCriteria` to your repository, which builds the correct sort. Nested paths such as `address.city` also work.
