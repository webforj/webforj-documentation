package com.webforj.samples.views.table;

import com.webforj.annotation.InlineStyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.table.Table;
import com.webforj.router.annotation.Route;

import java.util.ArrayList;
import java.util.List;

@Route
@InlineStyleSheet(/* css */ """
  dwc-table::part(row-even) {
    background-color: var(--dwc-color-surface-2);
  }

  dwc-table::part(row-highlight) {
    background-color: var(--dwc-color-info);
    color: var(--dwc-color-on-info-text);
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
        new Person("Frank", 45, "Miami")
    );

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
      if (data.indexOf(person) % 2 == 0) {
        parts.add("row-even");
      }
      return parts;
    });

    self.add(table);
  }

  public static class Person {
    private final String name;
    private final int age;
    private final String city;

    public Person(String name, int age, String city) {
      this.name = name;
      this.age = age;
      this.city = city;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }

    public String getCity() {
      return city;
    }
  }
} 
