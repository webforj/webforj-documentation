package com.webforj.samples.views.table;

import java.util.ArrayList;
import java.util.List;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Column;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.Route;

@Route
@InlineStyleSheet(/* css */ """
  dwc-table::part(row-even) {
    background-color: var(--dwc-color-gray-alt);
  }

  dwc-table::part(cell-senior) {
    background-color: var(--dwc-color-success-alt);
    color: var(--dwc-color-success-text);
  }

  dwc-table::part(cell-junior) {
    background-color: var(--dwc-color-danger-alt);
    color: var(--dwc-color-danger-text);
  }
""")
public class TableDynamicStylingView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();
  private final Table<Person> table = new Table<>();
  private final Button update = new Button("Increase Alice Age", ButtonTheme.GRAY);
  private boolean toggleAge = false;

  public TableDynamicStylingView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth(760);
    self.setStyle("margin", "1em auto");

    List<Person> data = List.of(
        new Person("Alice", 28, "New York"),
        new Person("Tom", 32, "Chicago"),
        new Person("Bob", 28, "Chicago"),
        new Person("Bob", 35, "New York"),
        new Person("Charlie", 25, "Los Angeles"),
        new Person("Charlie", 25, "New York"),
        new Person("David", 40, "San Francisco"),
        new Person("Eve", 30, "Boston"),
        new Person("Frank", 30, "Boston"),
        new Person("Grace", 27, "Seattle"));

    table.addColumn("Name", Person::getName).setSortable(true);
    Column<Person, Integer> ageColumn = table.addColumn("Age", Person::getAge).setSortable(true);
    table.addColumn("City", Person::getCity).setSortable(true);

    table.setItems(data);
    table.setWidth("100%");
    table.setHeight("400px");

    table.setRowPartProvider(p -> {
      List<String> parts = new ArrayList<>();
      int index = data.indexOf(p);
      parts.add(index % 2 == 0 ? "row-even" : "row-odd");
      return parts;
    });

    table.setCellPartProvider((person, column) -> {
      List<String> parts = new ArrayList<>();
      if (column == ageColumn) {
        if (person.getAge() > 30) {
          parts.add("cell-senior");
        } else {
          parts.add("cell-junior");
        }
      }
      return parts;
    });

    update.setMaxWidth(200);
    update.onClick(e -> {
      Person alice = data.get(0);
      alice.setAge(toggleAge ? 28 : 31);
      toggleAge = !toggleAge;
      table.getRepository().commit(alice);
    });

    self.add(update, table);
  }
}
