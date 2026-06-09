package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.table.Column
import com.webforj.component.table.renderer.VoidElementRenderer
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.vh
import com.webforj.kotlin.extension.vw
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Table Edit Data")
class TableEditDataKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      val table = table {
        repository = Service.getMusicRecords()
        rowHeight = 42.0
        width = 100.vw
        height = 100.vh
        addColumn("Number", MusicRecord::getNumber).apply {
          pinDirection = Column.PinDirection.LEFT
        }
        addColumn("Title", MusicRecord::getTitle)
        addColumn("Artist", MusicRecord::getArtist)
        addColumn("Genre", MusicRecord::getMusicType)
      }
      titleEditorComponent {
        val editRenderer = VoidElementRenderer("dwc-icon-button") {
          edit(it.item)
        }
        editRenderer.setAttribute("name", "pencil-pin")
        table.addColumn<MusicRecord>(editRenderer).apply {
          alignment = Column.Alignment.CENTER
          pinDirection = Column.PinDirection.RIGHT
        }
        onSave { table.repository.commit(it.item) }
      }
    }
  }
}
