package com.webforj.samples.views.table;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Table;
import com.webforj.component.table.Table.Border;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

import java.util.EnumSet;
import java.util.List;

@Route
@FrameTitle("Table Layout Styling")
public class TableLayoutStylingView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Table<Person> table = new Table<>();

  public TableLayoutStylingView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-xl)");

    // Use record instances
    List<Person> data = List.of(
        new Person("Alice", 28, "New York"),
        new Person("Bob", 35, "Chicago"),
        new Person("Charlie", 25, "Los Angeles"),
        new Person("David", 40, "San Francisco"),
        new Person("Eve", 30, "Boston"),
        new Person("Frank", 45, "Miami"));

    // Use record accessor methods (name, age, city)
    table.addColumn("Name", Person::name).setSortable(true);
    table.addColumn("Age", Person::age).setSortable(true);
    table.addColumn("City", Person::city).setSortable(true);

    table.setItems(data);
    table.setSize("100%", "260px");

    table.setStriped(true);

    table.setBordersVisible(EnumSet.of(Border.AROUND, Border.ROWS, Border.COLUMNS));

    self.add(table);
  }
}
