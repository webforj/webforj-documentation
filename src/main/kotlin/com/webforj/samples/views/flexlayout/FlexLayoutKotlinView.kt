package com.webforj.samples.views.flexlayout

import com.webforj.annotation.StyleSheet
import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.layout.flexlayout.FlexJustifyContent
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.component.list.ChoiceBox
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.maskedNumberField
import com.webforj.kotlin.dsl.component.field.passwordField
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontal
import com.webforj.kotlin.dsl.component.layout.flexlayout.horizontalReverse
import com.webforj.kotlin.dsl.component.layout.flexlayout.vertical
import com.webforj.kotlin.dsl.component.list.choiceBox
import com.webforj.kotlin.dsl.component.list.items
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.px
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@StyleSheet("ws://css/flexlayout/flexLayout.css")
@Route
@FrameTitle("Flex Layout")
class FlexLayoutKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      flexLayout {
        vertical()
        classNames + "main__layout"
        flexLayout {
          horizontal()
          wrap = FlexWrap.WRAP
          textField("Email")
          passwordField("Password")
        }
        flexLayout {
          horizontal()
          wrap = FlexWrap.WRAP
          textField("First Name")
          textField("Last Name")
        }
        flexLayout {
          horizontal()
          wrap = FlexWrap.WRAP
          textField("Address")
        }
        flexLayout {
          horizontal()
          justifyContent = FlexJustifyContent.BETWEEN
          setItemBasis(40.percent, textField("City"))
          setItemBasis(20.percent, choiceBox("State") {
            maxRowCount = 7
            populateStates()
          })
          setItemBasis(40.percent, maskedNumberField("Zip") {
            width = 150.px
          })
        }
        flexLayout {
          horizontalReverse()
          button("Cancel", ButtonTheme.DEFAULT)
          button("Submit", ButtonTheme.PRIMARY)
        }
      }
    }
  }

  private fun ChoiceBox.populateStates() {
    items("AL" to "Alabama",
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
    "WY" to "Wyoming"
    )
  }
}
