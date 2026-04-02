package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.table.Column
import com.webforj.component.table.Table
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.table.column
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.*
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import java.util.function.Function

@Route
@FrameTitle("Table Column Auto-Sizing")
class TableColumnAutoSizingKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private val table: Table<MusicRecord>

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      padding = "var(--dwc-space-l)"
      spacing = "var(--dwc-space-l)"
      height = 100.vh
      styles["box-sizing"] = "border-box"
      flexLayout {
        direction = FlexDirection.ROW
        styles["gap"] = 15.px
        styles["align-items"] = "center"
        styles["flex-wrap"] = "wrap"
        button("Auto-Size All Columns") {
          onClick { table.setColumnsToAutoSize() }
        }
        button("Auto-Fit to Table Width") {
          onClick { table.setColumnsToAutoFit() }
        }
        button("Auto-Size Title Only") {
          onClick {
            table.setColumnToAutoSize("Title")
          }
        }
        button("Reset to Default") {
          onClick {
            applyDefaultColumnSizing()
            table.refreshColumns()
          }
        }
      }
      table = table {
        repository = Service.getMusicRecords()
        width = 100.percent
        height = 450.px
        isStriped = true
        addColumn("Number", MusicRecord::getNumber)
        addColumn("Title", MusicRecord::getTitle)
        addColumn("Artist", MusicRecord::getArtist)
        addColumn("Genre", MusicRecord::getMusicType)
        addColumn("Cost") { String.format("$%.2f", it.cost) }.apply {
          alignment = Column.Alignment.RIGHT
        }
      }
    }
    applyDefaultColumnSizing()
  }

  private fun applyDefaultColumnSizing() {
    table.columns.forEach { it.flex = 0f }
    table.column("Number") {
      width = 50f
      minWidth = 40f
    }
    table.column("Title") {
      width = 100f
      minWidth = 80f
    }
    table.column("Artist") {
      width = 200f
      minWidth = 100f
    }
    table.column("Genre") {
      width = 80f
      minWidth = 60f
    }
    table.column("Cost") {
      width = 60f
      minWidth = 50f
    }
  }
}
