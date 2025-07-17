package com.webforj.samples.views.table;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;

// TODO refactor css.
@StyleSheet(/* css */ """
  dwc-table::part(cell-highlight) {
    background-color: var(--dwc-color-warning-alt);
    color: var(--dwc-color-warning-text);
    font-weight: bold;
  }
""")
@Route
public class TableCellStylingView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Table<Person> table = new Table<>();

  public TableCellStylingView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-xl)");

    List<Person> data = List.of(
        new Person("Alice", 28, "New York"),
        new Person("Bob", 35, "Chicago"),
        new Person("Charlie", 25, "Los Angeles"),
        new Person("David", 40, "San Francisco"),
        new Person("Eve", 30, "Boston"),
        new Person("Frank", 45, "Miami")
    );

    Column<Person, String> nameCol = table.addColumn("Name", Person::getName).setSortable(true);
    Column<Person, Integer> ageCol = table.addColumn("Age", Person::getAge).setSortable(true);
    Column<Person, String> cityCol = table.addColumn("City", Person::getCity).setSortable(true);

    table.setItems(data);
    table.setSize("100%", "260px");

    table.setCellPartProvider((person, column) -> {
      List<String> parts = new ArrayList<>();
      if (column == ageCol && person.getAge() > 30) {
        parts.add("cell-highlight");
      }
      return parts;
    });

    self.add(table);
  }
}
