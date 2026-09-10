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
@FrameTitle("Table Column Group Ordering")
class TableColumnGroupOrderingKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table<MusicRecord> {
        repository = Service.getMusicRecords()
        width = 100.vw
        height = 100.vh
        isStriped = true

        addColumn("Number", MusicRecord::getNumber).apply { minWidth = 70f }
        addColumn("Title", MusicRecord::getTitle).apply { minWidth = 120f }
        addColumn("Artist", MusicRecord::getArtist).apply { minWidth = 120f }
        addColumn("Genre", MusicRecord::getMusicType).apply { minWidth = 80f }
        addColumn("Label", MusicRecord::getLabel).apply { minWidth = 80f }
        addColumn("Cost") { String.format("$%.2f", it.cost) }.apply {
          alignment = Column.Alignment.RIGHT
          minWidth = 70f
        }

        setColumnsToAutoFit()

        val music = ColumnGroup.of("music", "Music")
          .add("Title")
          .add("Artist")
          .add("Genre")

        val pricing = ColumnGroup.of("pricing", "Pricing")
          .add("Cost")

        columnGroups = listOf(music, pricing)
      }
    }
  }
}
