package com.webforj.samples.views.table

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.table.Column
import com.webforj.component.table.ColumnGroup
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.vh
import com.webforj.kotlin.extension.vw
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/table/tablestyledcolumngroups.css")
@Route
@FrameTitle("Table Styled Column Groups")
class TableStyledColumnGroupsKotlinView : Composite<Div>() {
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
        addColumn("Tracks", MusicRecord::getNumberOfTracks).apply { minWidth = 60f }
        addColumn("Playing Time", MusicRecord::getPlayingTime).apply { minWidth = 80f }
        addColumn("Cost") { String.format("$%.2f", it.cost) }.apply {
          alignment = Column.Alignment.RIGHT
          minWidth = 70f
        }
        addColumn("Retail") { String.format("$%.2f", it.retail) }.apply {
          alignment = Column.Alignment.RIGHT
          minWidth = 70f
        }

        setColumnsToAutoFit()

        val catalog = ColumnGroup.of("catalog", "Catalog")
          .add("Title")
          .add("Artist")
          .add("Genre")

        val details = ColumnGroup.of("details", "Details")
          .add(ColumnGroup.of("media", "Media").add("Tracks").add("Playing Time"))
          .add(ColumnGroup.of("pricing", "Pricing").add("Cost").add("Retail"))

        columnGroups = listOf(catalog, details)
      }
    }
  }
}
