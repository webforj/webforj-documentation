package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.table.Table
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.vh
import com.webforj.kotlin.extension.vw
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Table Multi Sorting")
class TableMultiSortingKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table {
        repository = Service.getMusicRecords()
        width = 100.vw
        height = 100.vh
        isMultiSorting = true
        addColumn("Title", MusicRecord::getTitle)
        addColumn("Artist", MusicRecord::getArtist)
        addColumn("Genre", MusicRecord::getMusicType)
        addColumn("Cost", MusicRecord::getCost)
        columns.forEach { it.isSortable = true }
      }
    }
  }
}
