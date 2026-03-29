package com.webforj.samples.views.table

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.webforj.component.Composite
import com.webforj.component.field.TextField
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexContentAlignment
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.navigator.Navigator
import com.webforj.component.table.Column
import com.webforj.component.table.Table
import com.webforj.data.Paginator
import com.webforj.data.repository.CollectionRepository
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.dsl.component.navigator.navigator
import com.webforj.kotlin.dsl.component.table.column
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.utilities.Assets

@Route
@FrameTitle("Data Table")
class DataTableKotlinView : Composite<Div>() {
  private var searchTerm = ""
  private val repository: CollectionRepository<JsonObject>
  private val self = boundComponent
  private lateinit var paginator: Paginator

  init {
    val data = Gson().fromJson<List<JsonObject>>(
      Assets.contentOf(Assets.resolveContextUrl("context://data/olympic-winners.json")),
      object : TypeToken<List<JsonObject>>() {}.type
    )
    repository = CollectionRepository(data)
    repository.setBaseFilter {
     searchTerm in it.get("athlete").asString.lowercase()
    }
    paginator = Paginator(repository).apply {
      max = 5
    }
    self.apply {
      flexLayout {
        vertical()
        alignContent = FlexContentAlignment.CENTER
        padding = "var(--dwc-space-l)"
        flexLayout {
          horizontal()
          justifyContent = FlexJustifyContent.BETWEEN
          choiceBox("Entries per page") {
            items("10", "25", "50", "100")
            selectIndex(0)
            onSelect { paginator.size = it.selectedItem.text.toInt() }
          }
          textField("Search", type = TextField.Type.SEARCH) {
            placeholder = "Search by athlete..."
            onModify {
              searchTerm = it.text.lowercase()
              paginator.current = 1
              repository.commit()
            }
          }
        }
        table<JsonObject> {
          this.repository = this@DataTableKotlinView.repository
          height = 400.px
          selectionMode = Table.SelectionMode.MULTIPLE
          isHeaderCheckboxSelection = false
          listOf("athlete", "age", "country", "year", "total").forEach { column ->
            addColumn(column) {
              val element = it.get(column)
              if (!element.isJsonNull) {
                element.asString
              } else {
                ""
              }
            }
          }
          columns.forEach { it.isSortable = true }
          column("athlete") {
            pinDirection = Column.PinDirection.LEFT
            minWidth = 200f
          }
          column("total") {
            pinDirection = Column.PinDirection.RIGHT
          }
        }
        flexLayout {
          horizontal()
          justifyContent = FlexJustifyContent.BETWEEN
          navigator {
            layout = Navigator.Layout.PAGES
            paginator = this@DataTableKotlinView.paginator
            isAutoDisable = true
          }
          navigator {
            layout = Navigator.Layout.PREVIEW
            paginator = this@DataTableKotlinView.paginator
            isHideMainButtons = true
            styles["border"] = "0"
            text = $$"`Showing ${startIndex + 1} to ${endIndex + 1} of ${totalItems} entries`"
          }
        }
      }
    }
  }
}
