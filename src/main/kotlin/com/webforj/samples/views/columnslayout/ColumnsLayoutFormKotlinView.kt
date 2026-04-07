package com.webforj.samples.views.columnslayout

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.html.elements.Div
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.passwordField
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.columnslayout.columnsLayout
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.extension.dvh
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Columns Layout Form")
class ColumnsLayoutFormKotlinView : Composite<Div>() {
  private val self = boundComponent

  init {
    self.apply {
      maxWidth = 600.px
      styles["margin"] = "0 auto"
      styles["overflow"] = "auto"
      styles["height"] = 100.dvh
      columnsLayout {
        styles["padding"] = "var(--dwc-space-xl)"
        textField("First Name")
        textField("Last Name")
        setSpan(textField("Email"), 2)
        passwordField("Password")
        passwordField("Confirm Password")
        setSpan(textField("Address"), 2)
        choiceBox("State") {
          items(
            "AL" to "Alabama",
            "AK" to "Alaska",
            "AZ" to "Arizona",
            "AR" to "Arkansas",
            "CA" to "California",
            "CO" to "Colorado",
            "CT" to "Connecticut",
            "DE" to "Delaware",
            "FL" to "Florida",
            "GA" to "Georgia",
            "HI" to "Hawaii",
            "ID" to "Idaho",
            "IL" to "Illinois",
            "IN" to "Indiana",
            "IA" to "Iowa",
            "KS" to "Kansas",
            "KY" to "Kentucky",
            "LA" to "Louisiana",
            "ME" to "Maine",
            "MD" to "Maryland",
            "MA" to "Massachusetts",
            "MI" to "Michigan",
            "MN" to "Minnesota",
            "MS" to "Mississippi",
            "MO" to "Missouri",
            "MT" to "Montana",
            "NE" to "Nebraska",
            "NV" to "Nevada",
            "NH" to "New Hampshire",
            "NJ" to "New Jersey",
            "NM" to "New Mexico",
            "NY" to "New York",
            "NC" to "North Carolina",
            "ND" to "North Dakota",
            "OH" to "Ohio",
            "OK" to "Oklahoma",
            "OR" to "Oregon",
            "PA" to "Pennsylvania",
            "RI" to "Rhode Island",
            "SC" to "South Carolina",
            "SD" to "South Dakota",
            "TN" to "Tennessee",
            "TX" to "Texas",
            "UT" to "Utah",
            "VT" to "Vermont",
            "VA" to "Virginia",
            "WA" to "Washington",
            "WV" to "West Virginia",
            "WI" to "Wisconsin",
            "WY" to "Wyoming",
          )
        }
        textField("Zip")
        button("Cancel", ButtonTheme.OUTLINED_PRIMARY) {
          styles["margin-top"] = "var(--dwc-space-l)"
        }
        button("Submit", ButtonTheme.PRIMARY) {
          styles["margin-top"] = "var(--dwc-space-l)"
        }
      }
    }
  }
}
