package com.webforj.samples.views.navigator

import com.webforj.component.Composite
import com.webforj.component.html.elements.Div
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.navigator.Navigator
import com.webforj.component.navigator.Navigator.Layout
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.navigator.navigator
import com.webforj.kotlin.dsl.component.table.column
import com.webforj.kotlin.dsl.component.table.table
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route
import com.webforj.samples.views.table.MusicRecord
import com.webforj.samples.views.table.Service

@Route
@FrameTitle("Navigator Table")
class NavigatorTableKotlinView: Composite<Div>() {

  init {
    val repo = Service.getMusicRecords()
      boundComponent.apply {
        flexLayout {
          vertical()
          table<MusicRecord> {
            height = "400px"
            addColumn("Number", MusicRecord::getNumber)
            addColumn("Title", MusicRecord::getTitle)
            addColumn("Artist", MusicRecord::getArtist)
            addColumn("Genre", MusicRecord::getMusicType)
            addColumn("Cost", MusicRecord::getCost)
            repository = repo
          }
          val nav = navigator(repository = repo, layout = Layout.PAGES) {
            isAutoDisable = true
            paginator.max = 5
            styles["margin-right"] = "20px"
          }
          setItemAlignment(FlexAlignment.END, nav)
        }
      }
  }
}
