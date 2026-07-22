package com.webforj.samples.views.table;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.Route;
import java.util.ArrayList;
import java.util.List;

@Route
@BundleEntry("css/table/table-row-styling-view.css")
public class TableRowStylingView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Table<Person> table = new Table<>();

  public TableRowStylingView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-l)")
        .setPadding("var(--dwc-space-xl)")
        .add(table);

    List<Person> data =
        List.of(
            new Person("Alice", 28, "New York"),
            new Person("Bob", 35, "Chicago"),
            new Person("Charlie", 25, "Los Angeles"),
            new Person("David", 40, "San Francisco"),
            new Person("Eve", 30, "Boston"),
            new Person("Frank", 45, "Miami"));

    table.addColumn("Name", Person::getName).setSortable(true).setFlex(1f).setMinWidth(120f);
    table.addColumn("Age", Person::getAge).setSortable(true).setFlex(1f).setMinWidth(80f);
    table.addColumn("City", Person::getCity).setSortable(true).setFlex(1f).setMinWidth(140f);

    table
        .setItems(data)
        .setSize("100%", "260px")
        .setRowPartProvider(
            person -> {
              List<String> parts = new ArrayList<>();
              if (person.getAge() > 30) {
                parts.add("row-highlight");
              }

              return parts;
            });
  }
}
