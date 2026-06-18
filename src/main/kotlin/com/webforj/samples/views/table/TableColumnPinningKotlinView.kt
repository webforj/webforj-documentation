package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.table.Column
import com.webforj.component.table.Table
import com.webforj.component.table.renderer.ButtonRenderer
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.component.optiondialog.OptionDialog
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.vh
import com.webforj.kotlin.extension.vw

@Route
@FrameTitle("Table Column Pinning")
class TableColumnPinningKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table<MusicRecord> {
        repository = Service.getMusicRecords()
        width = 100.vw
        height = 100.vh
        rowHeight = 42.0
        setColumnsToMovable(false)
        addColumn("Number", MusicRecord::getNumber).apply {
          pinDirection = Column.PinDirection.LEFT
        }
        addColumn("Title", MusicRecord::getTitle)
        addColumn("Artist", MusicRecord::getArtist)
        addColumn("Tracks Number", MusicRecord::getNumberOfTracks)
        addColumn("Genre", MusicRecord::getMusicType)
        addColumn("Cost") { String.format("$%.2f", it.cost) }.apply {
          alignment = Column.Alignment.RIGHT
        }
        addColumn<MusicRecord>(ButtonRenderer("Edit") {
          OptionDialog.showMessageDialog(
            "You asked to edit record number <b>${it.item.number}</b>.",
            "Edit Record"
          )
        }).apply {
          alignment = Column.Alignment.CENTER
          pinDirection = Column.PinDirection.RIGHT
        }
      }
    }
  }
}
