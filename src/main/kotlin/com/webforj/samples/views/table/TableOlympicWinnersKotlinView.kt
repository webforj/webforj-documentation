package com.webforj.samples.views.table

import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.table.Column
import com.webforj.component.table.Table
import com.webforj.kotlin.dsl.component.table.column
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.vh
import com.webforj.kotlin.extension.vw
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.utilities.Assets

@Route
@FrameTitle("Olympic Winners Table")
class TableOlympicWinnersKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table {
        items = Gson().fromJson<List<JsonObject>>(
          Assets.contentOf(Assets.resolveContextUrl("context://data/olympic-winners.json")),
          object : TypeToken<List<JsonObject>>() {}.type
        )
        width = 100.vw
        height = 100.vh
        isClientSorting = true
        listOf("athlete", "age", "country", "year", "sport", "gold", "silver", "bronze", "total").forEach {
          addColumn(it) { person ->
            val element = person.get(it)
            if (!element.isJsonNull) {
              element.asString
            } else {
              ""
            }
          }
        }
        columns.forEach { it.isSortable }
        column("athlete") {
          pinDirection = Column.PinDirection.LEFT
          minWidth = 200f
        }
        column("total") {
          pinDirection = Column.PinDirection.RIGHT
        }
      }
    }
  }
}
