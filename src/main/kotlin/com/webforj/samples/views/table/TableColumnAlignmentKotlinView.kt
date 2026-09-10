package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.table.Column
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.vh
import com.webforj.kotlin.extension.vw
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Table Column Alignment")
class TableColumnAlignmentKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table {
        repository = Service.getMusicRecords()
        width = 100.vw
        height = 100.vh
        addColumn("Number", MusicRecord::getNumber)
        addColumn("Title", MusicRecord::getTitle)
        addColumn("Genre", MusicRecord::getMusicType)
        addColumn("Cost") { String.format("$%.2f", it.cost) }.apply {
          alignment = Column.Alignment.RIGHT }
      }
    }
  }
}
