package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.field.NumberField
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.component.table.Column
import com.webforj.component.table.Table
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.numberField
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.table.column
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.*
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Table Column Flexible Sizing")
class TableColumnFlexSizingKotlinView : Composite<FlexLayout>() {
  private val self = boundComponent
  private val table: Table<MusicRecord>
  private val fields = hashMapOf<String, NumberField>()

  init {
    self.apply {
      direction = FlexDirection.COLUMN
      padding = "var(--dwc-space-l)"
      spacing = "var(--dwc-space-l)"
      height = 100.vh

      flexLayout {
        direction = FlexDirection.ROW
        spacing = "var(--dwc-space-l)"
        alignment = FlexAlignment.END
        wrap = FlexWrap.WRAP

        flexControl("Title", 2.0) {
          updateColumnFlex(it, "Title")
        }
        flexControl("Artist", 1.5) {
          updateColumnFlex(it, "Artist")
        }
        flexControl("Genre", 1.0) {
          updateColumnFlex(it, "Genre")
        }
        button("Reset to Defaults", ButtonTheme.OUTLINED_PRIMARY) {
          onClick { resetToDefaults() }
        }
        button("Equal Flex", ButtonTheme.OUTLINED_PRIMARY) {
          onClick { setEqualFlex() }
        }
      }
      table = table {
        repository = Service.getMusicRecords()
        width = 100.percent
        height = 400.px
        isStriped = true
        addColumn("Number", MusicRecord::getNumber).apply {
          width = 80f
          isResizable = false
        }
        addColumn("Title", MusicRecord::getTitle).apply {
          flex = 2f
          minWidth = 120f
          isResizable = true
        }
        addColumn("Artist", MusicRecord::getArtist).apply {
          flex = 1.5f
          minWidth = 100f
          isResizable = true
        }
        addColumn("Genre", MusicRecord::getMusicType).apply {
          flex = 1f
          minWidth = 80f
          isResizable = true
        }
        addColumn("Cost") { String.format("$%.2f", it.cost) }.apply {
          width = 80f
          alignment = Column.Alignment.RIGHT
          isResizable = false
        }
      }
    }
  }

  private fun FlexLayout.flexControl(
    label: String,
    defaultValue: Double,
    onChange: (Float) -> Unit
  ) {
    flexLayout {
      direction = FlexDirection.COLUMN
      spacing = 5.px
      minWidth = 120.px

      paragraph("$label Flex:") {
        styles["margin"] = "0"
        styles["font-weight"] = "bold"
        styles["font-size"] = 14.px
      }
      val field = numberField(value = defaultValue) {
        min = 0.1
        max = 10.0
        step = 0.1
        width = 100.px
        onModify { onChange(value.toFloat()) }
      }
      fields[label] = field
    }
  }

  private fun updateColumnFlex(value: Float, column: String) {
    value.takeIf { it > 0 }?.let {
      table.column(column) {
        flex = value
      }
      table.refreshColumns()
    }
  }

  private fun resetToDefaults() {
    fields["Title"]?.value = 2.0
    fields["Artist"]?.value = 1.5
    fields["Genre"]?.value = 1.0

    table.column("Title") { flex = 2f }
    table.column("Artist") { flex = 1.5f }
    table.column("Genre") { flex = 1f }
    table.refreshColumns()
  }

  private fun setEqualFlex() {
    fields.values.forEach { it.value = 1.0 }

    table.column("Title") { flex = 1f }
    table.column("Artist") { flex = 1f }
    table.column("Genre") { flex = 1f }
    table.refreshColumns()
  }

}
