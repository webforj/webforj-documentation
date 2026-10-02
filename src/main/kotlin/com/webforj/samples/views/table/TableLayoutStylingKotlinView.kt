package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.table.Table
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.size
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Table Layout Styling")
class TableLayoutStylingKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      spacing = "var(--dwc-space-l)"
      margin = "var(--dwc-space-xl)"

      table {
        size = 100.percent to 260.px
        isStriped = true
        bordersVisible = setOf(Table.Border.AROUND, Table.Border.ROWS, Table.Border.COLUMNS)
        items = listOf(
          Person("Alice", 28, "New York"),
          Person("Bob", 35, "Chicago"),
          Person("Charlie", 25, "Los Angeles"),
          Person("David", 40, "San Francisco"),
          Person("Eve", 30, "Boston"),
          Person("Frank", 45, "Miami")
        )
        addColumn("Name", Person::getName).apply {
          isSortable = true
        }
        addColumn("Age", Person::getAge).apply {
          isSortable = true
        }
        addColumn("City", Person::getCity).apply {
          isSortable = true
        }
      }
    }
  }
}
