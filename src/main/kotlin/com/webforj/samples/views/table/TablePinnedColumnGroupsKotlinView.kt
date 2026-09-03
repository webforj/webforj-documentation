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
@FrameTitle("Table Pinned Column Groups")
class TablePinnedColumnGroupsKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table<MusicRecord> {
        repository = Service.getMusicRecords()
        width = 100.vw
        height = 100.vh
        isStriped = true

        addColumn("Number", MusicRecord::getNumber).apply { minWidth = 90f }
        addColumn("Title", MusicRecord::getTitle).apply { minWidth = 200f }
        addColumn("Artist", MusicRecord::getArtist).apply { minWidth = 200f }
        addColumn("Genre", MusicRecord::getMusicType).apply { minWidth = 150f }
        addColumn("Label", MusicRecord::getLabel).apply { minWidth = 150f }
        addColumn("Cost") { String.format("$%.2f", it.cost) }.apply {
          alignment = Column.Alignment.RIGHT
          minWidth = 100f
        }

        val identity = ColumnGroup.of("identity", "Identity")
          .setPinDirection(Column.PinDirection.LEFT)
          .add("Number")
          .add("Title")

        val catalog = ColumnGroup.of("catalog", "Catalog")
          .add("Artist")
          .add("Genre")
          .add("Label")

        val pricing = ColumnGroup.of("pricing", "Pricing")
          .add("Cost")

        columnGroups = listOf(identity, catalog, pricing)
      }
    }
  }
}
