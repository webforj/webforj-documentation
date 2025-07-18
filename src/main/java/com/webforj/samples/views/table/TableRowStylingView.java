package com.webforj.samples.views.table;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;

@Route("tablerowstyling")
@InlineStyleSheet(/* css */ """
      dwc-table::part(row-highlight) {
        background-color: var(--dwc-color-info-alt);
        color: var(--dwc-color-info-text);
        font-weight: bold;
      }
    """)
public class TableRowStylingView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Table<Person> table = new Table<>();

  public TableRowStylingView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-xl)");

    List<Person> data = List.of(
        new Person("Alice", 28, "New York"),
        new Person("Bob", 35, "Chicago"),
        new Person("Charlie", 25, "Los Angeles"),
        new Person("David", 40, "San Francisco"),
        new Person("Eve", 30, "Boston"),
        new Person("Frank", 45, "Miami"));

    table.addColumn("Name", Person::getName).setSortable(true);
    table.addColumn("Age", Person::getAge).setSortable(true);
    table.addColumn("City", Person::getCity).setSortable(true);

    table.setItems(data);
    table.setSize("100%", "260px");

    table.setRowPartProvider(person -> {
      List<String> parts = new ArrayList<>();
      if (person.getAge() > 30) {
        parts.add("row-highlight");
      }

      return parts;
    });

    self.add(table);
  }
}
