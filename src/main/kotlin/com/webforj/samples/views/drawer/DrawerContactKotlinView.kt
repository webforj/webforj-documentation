package com.webforj.samples.views.drawer

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.drawer.Drawer
import com.webforj.component.drawer.Drawer.Placement
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.button.icon
import com.webforj.kotlin.dsl.component.drawer.drawer
import com.webforj.kotlin.dsl.component.html.elements.paragraph
import com.webforj.kotlin.dsl.component.icons.tablerIcon
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/drawer/drawerContact.css")
@Route
@FrameTitle("Contact Picker")
class DrawerContactKotlinView: Composite<FlexLayout>() {
  private val drawer: Drawer

  init {
      boundComponent.apply {
        margin = "var(--dwc-space-l)"
        button("Open Contacts") {
          onClick { drawer.open() }
        }
        drawer = drawer("Contacts") {
          placement = Placement.BOTTOM_CENTER
          flexLayout(FlexDirection.COLUMN) {
            addClassName("contact-list")
            createContact("Gregory Baldrake", "US - Albuquerque", "GB", "#fdca8b")
            createContact("Betsy Heebink", "US - Madison", "BH", "#85cf8a")
            createContact("Wesley Osborn", "US - Seattle", "WO", "#d4df4a")
            createContact("Harry Chuckie", "US - Palm Springs", "HC", "#00ffb7")
            createContact("Stephanie McIntyre", "US - Modesto", "SM", "#ff6230")
            createContact("Dave Strum", "US - Hagerstown", "DS", "#b88bfa")
            createContact("Dr. Jane Booker", "US - Hagerstown", "DB", "#4c7c4b")
          }
          open()
        }
      }
  }

  private fun FlexLayout.createContact(name: String, location: String, initials: String, color: String) {
    flexLayout {
      addClassName("contact-row")
      wrap = FlexWrap.WRAP
      alignment = FlexAlignment.AUTO
      justifyContent = FlexJustifyContent.EVENLY
      flexLayout {
        addClassName("contact-avatar")
        text = initials
        styles["background-color"] = color
      }
      flexLayout(FlexDirection.COLUMN) {
        addClassName("contact-text")
        paragraph(name).addClassName("contact-name")
        paragraph(location).addClassName("contact-location")
      }
      button {
        addClassName("contact-call")
        icon { tablerIcon("phone") }
        theme = ButtonTheme.DEFAULT
      }
    }
  }
}
