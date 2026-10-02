package com.webforj.samples.views.table

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.table.Table
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.size
import com.webforj.router.annotation.Route

@Route
@InlineStyleSheet(/* css */ """
  dwc-table::part(cell-highlight) {
    background-color: var(--dwc-color-warning-alt);
    color: var(--dwc-color-warning-text);
    font-weight: bold;
  }
""")
class TableCellStylingKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-l)"
      margin = "var(--dwc-space-xl)"

      table<Person> {
        size = 100.percent to 260.px
        addColumn("Name", Person::getName).apply {
          isSortable = true
        }
        val ageCol = addColumn("Age", Person::getAge).apply {
          isSortable = true
        }
        addColumn("City", Person::getCity).apply {
          isSortable = true
        }
        items = listOf(
          Person("Alice", 28, "New York"),
          Person("Bob", 35, "Chicago"),
          Person("Charlie", 25, "Los Angeles"),
          Person("David", 40, "San Francisco"),
          Person("Eve", 30, "Boston"),
          Person("Frank", 45, "Miami")
        )
        setCellPartProvider { person, column ->
          val parts = mutableListOf<String>()
          if (column == ageCol && person.age > 30) {
            parts.add("cell-highlight")
          }
          parts
        }
      }
    }
  }
}
