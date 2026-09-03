package com.webforj.samples.views.table

import com.webforj.annotation.InlineStyleSheet
import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.Route

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
class TableDynamicStylingKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private var toggleAge = false

  init {
    val data = listOf(
      Person("Alice", 28, "New York"),
      Person("Tom", 32, "Chicago"),
      Person("Bob", 28, "Chicago"),
      Person("Bob", 35, "New York"),
      Person("Charlie", 25, "Los Angeles"),
      Person("Charlie", 25, "New York"),
      Person("David", 40, "San Francisco"),
      Person("Eve", 30, "Boston"),
      Person("Frank", 30, "Boston"),
      Person("Grace", 27, "Seattle")
    )
    self.apply {
      direction = FlexDirection.COLUMN
      maxWidth = 760.px
      styles["margin"] = "1em auto"

      val update = button("Increase Alice Age", ButtonTheme.GRAY) {
        maxWidth = 200.px
      }
      val table = table {
        items = data
        width = 100.percent
        height = 400.px
        addColumn("Name", Person::getName).apply {
          isSortable = true
        }
        addColumn("Age", Person::getAge).apply {
          isSortable = true
        }
        addColumn("City", Person::getCity).apply {
          isSortable = true
        }
        setRowPartProvider {
          val index = data.indexOf(it)
          val part = if (index % 2 == 0) {
            "row-even"
          } else {
            "row-odd"
          }
          listOf(part)
        }
        setCellPartProvider { person, column ->
          column.takeIf { it.id == "Age" }?.let {
            person.takeIf { it.age > 30 }?.let {
              listOf("cell-senior")
            } ?: listOf("cell-junior")
          } ?: listOf()
        }
      }
      update.onClick {
        val alice = data.first()
        alice.age = if (toggleAge) {
          28
        } else {
          31
        }
        toggleAge = !toggleAge
        update.text = if (toggleAge) {
          "Decrease Alice Age"
        } else {
          "Increase Alice Age"
        }
        table.repository.commit(alice)
      }
    }
  }
}
