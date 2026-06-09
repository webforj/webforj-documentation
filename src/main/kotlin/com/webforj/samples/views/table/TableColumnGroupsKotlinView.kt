package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.table.Column
import com.webforj.component.table.ColumnGroup
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.vh
import com.webforj.kotlin.extension.vw
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Table Column Groups")
class TableColumnGroupsKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table<MusicRecord> {
        repository = Service.getMusicRecords()
        width = 100.vw
        height = 100.vh
        isStriped = true

        addColumn("Title", MusicRecord::getTitle).apply { minWidth = 120f }
        addColumn("Artist", MusicRecord::getArtist).apply { minWidth = 120f }
        addColumn("Genre", MusicRecord::getMusicType).apply { minWidth = 80f }
        addColumn("Cost") { String.format("$%.2f", it.cost) }.apply {
          alignment = Column.Alignment.RIGHT
          minWidth = 70f
        }

        setColumnsToAutoFit()

        val catalog = ColumnGroup.of("catalog", "Catalog")
          .add("Title")
          .add("Artist")
          .add("Genre")

        val pricing = ColumnGroup.of("pricing", "Pricing")
          .add("Cost")

        columnGroups = listOf(catalog, pricing)
      }
    }
  }
}
