package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.table.Column
import com.webforj.component.table.ColumnGroup
import com.webforj.component.table.Table
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.rem
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.kotlin.extension.vh
import com.webforj.kotlin.extension.vw
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Table Hidden Columns in Groups")
class TableHiddenColumnGroupsKotlinView : Composite<Div>() {
  private val self = boundComponent
  private lateinit var table: Table<MusicRecord>
  private lateinit var retailColumn: Column<MusicRecord, *>

  init {
    self.apply {
      flexLayout {
        direction = FlexDirection.COLUMN
        height = 100.vh

        button("Toggle Retail Column", ButtonTheme.PRIMARY) {
          maxWidth = 200.px
          styles["margin-top"] = 1.rem
          styles["margin-left"] = 1.rem
          onClick {
            retailColumn.isHidden = !retailColumn.isHidden
            table.refreshColumns()
          }
        }
        table = table<MusicRecord> {
          repository = Service.getMusicRecords()
          width = 100.vw
          isStriped = true

          addColumn("Title", MusicRecord::getTitle).apply { minWidth = 120f }
          addColumn("Artist", MusicRecord::getArtist).apply { minWidth = 120f }
          addColumn("Genre", MusicRecord::getMusicType).apply { minWidth = 80f }
          addColumn("Cost") { String.format("$%.2f", it.cost) }.apply {
            alignment = Column.Alignment.RIGHT
            minWidth = 70f
          }
          retailColumn = addColumn("Retail") { String.format("$%.2f", it.retail) }.apply {
            alignment = Column.Alignment.RIGHT
            minWidth = 70f
          }

          setColumnsToAutoFit()

          retailColumn.isHidden = true

          val catalog = ColumnGroup.of("catalog", "Catalog")
            .add("Title")
            .add("Artist")
            .add("Genre")

          val pricing = ColumnGroup.of("pricing", "Pricing")
            .add("Cost")
            .add("Retail")

          columnGroups = listOf(catalog, pricing)
        }
      }
    }
  }
}
