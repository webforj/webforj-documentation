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
@FrameTitle("Table Single Selection")
class TableSingleSelectionKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table {
        repository = Service.getMusicRecords()
        width = 100.vw
        height = 100.vh
        selectionMode = Table.SelectionMode.SINGLE
        addColumn("Number", MusicRecord::getNumber)
        addColumn("Title", MusicRecord::getTitle)
        addColumn("Artist", MusicRecord::getArtist)
        addColumn("Genre", MusicRecord::getMusicType)
        addColumn("Cost", MusicRecord::getCost)
        onItemSelect {
          OptionDialog.showMessageDialog(
            "You have selected ${it.item.title} by ${it.item.artist}",
            "Record Number ${it.item.number}"

          )
        }
      }
    }
  }
}
