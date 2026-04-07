package com.webforj.samples.views.table

import com.webforj.component.Composite
import com.webforj.component.field.TextField
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexContentAlignment
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Table Filtering")
class TableFilteringKotlinView : Composite<Div>() {
  private val self = boundComponent
  private var searchTerm = ""

  init {
    val repository = Service.getMusicRecords().apply {
      setBaseFilter { searchTerm in it.title.lowercase() }
    }
    self.apply {
      styles["padding"] = 30.px
      flexLayout {
        vertical()
        alignContent = FlexContentAlignment.CENTER
        height = 500.px
        textField("Search", type = TextField.Type.SEARCH) {
          placeholder = "Search by title..."
          onModify {
            searchTerm = it.text.lowercase()
            repository.commit()
          }
        }
        table {
          this.repository = repository
          addColumn("Title", MusicRecord::getTitle)
          addColumn("Artist", MusicRecord::getArtist)
          addColumn("Genre", MusicRecord::getMusicType)
          addColumn("Cost", MusicRecord::getCost)
        }
      }
    }
  }

}
