package com.webforj.samples.views.table

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.table.Column
import com.webforj.component.table.Table
import com.webforj.component.table.renderer.AvatarRenderer
import com.webforj.component.table.renderer.BadgeRenderer
import com.webforj.component.table.renderer.Renderer
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.plus
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/table/tableRichContent.css")
@Route
@FrameTitle("Table Rich Content")
class TableRichContentKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      table {
        repository = Service.getMusicRecords()
        classNames + "table"
        selectionMode = Table.SelectionMode.MULTIPLE
        rowHeight = 45.0
        addColumn("Title", MusicRecord::getTitle).apply { isHidden = true }
        addColumn("Artist", MusicRecord::getArtist).apply { isHidden = true }
        addColumn<MusicRecord>("Title & Artist", AvatarRenderer()).apply {
          flex = 1f
          minWidth = 200f
        }
        getColumnById("Genre").apply { pinDirection = Column.PinDirection.RIGHT }
        addColumn("Cost", MusicRecord::getCost).apply {
          setRenderer(BadgeRenderer())
          pinDirection = Column.PinDirection.RIGHT
        }
        refreshColumns()
      }
    }
  }
}
