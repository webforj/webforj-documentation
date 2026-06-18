package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.table.Table
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.component.optiondialog.OptionDialog
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.vh
import com.webforj.kotlin.extension.vw

@Route
@FrameTitle("Table Multiple Selection")
class TableMultiSelectionKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table {
        repository = Service.getMusicRecords()
        width = 100.vw
        height = 100.vh
        selectionMode = Table.SelectionMode.MULTIPLE
        addColumn("Number", MusicRecord::getNumber)
        addColumn("Title", MusicRecord::getTitle)
        addColumn("Artist", MusicRecord::getArtist)
        addColumn("Genre", MusicRecord::getMusicType)
        addColumn("Cost", MusicRecord::getCost)
        onItemSelectionChange { ev ->
          val records = ev.selectedItems
          val msg = if (records.isEmpty()) {
            "There are no records selected"
          } else {
            """<html> You have selected the following records${
              records.joinToString("", "<ul>", "</ul>") { "<li>${it.title}</li>" }
            }</html>"""
          }
          OptionDialog.showMessageDialog(msg, "Record Selection")
        }
      }
    }
  }
}
