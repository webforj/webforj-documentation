package com.webforj.samples.views.button

import com.webforj.component.Composite
import com.webforj.component.button.ButtonTheme
import com.webforj.component.field.TextField
import com.webforj.component.field.TextField.Type
import com.webforj.component.layout.flexlayout.*
import com.webforj.component.optiondialog.OptionDialog.showMessageDialog
import com.webforj.kotlin.dsl.component.button.button
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.kotlin.dsl.component.layout.flexlayout.flexLayout
import com.webforj.kotlin.extension.classNames
import com.webforj.kotlin.extension.percent
import com.webforj.kotlin.extension.plus
import com.webforj.kotlin.extension.plusAssign
import com.webforj.kotlin.extension.px
import com.webforj.kotlin.extension.set
import com.webforj.kotlin.extension.styles
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Button Demo")
class ButtonKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent
  private lateinit var firstName: TextField
  private lateinit var lastName: TextField
  private lateinit var email: TextField

  init {
      self.apply {
        direction = FlexDirection.COLUMN
        spacing = "var(--dwc-space-l)"
        alignment = FlexAlignment.CENTER
        margin = "var(--dwc-space-l)"

        flexLayout {
          justifyContent = FlexJustifyContent.CENTER
          wrap = FlexWrap.WRAP
          spacing = "var(--dwc-space-l)"
          width = 50.percent
          classNames += "row"

          firstName = textField("First Name", "Jason", type = Type.TEXT) {
            width = 25.percent
            styles["flex"] = "1"
          }
          lastName = textField("Last Name", "Turner", type = Type.TEXT) {
            width = 25.percent
            styles["flex"] = "1"
          }
        }
        flexLayout {
          justifyContent = FlexJustifyContent.CENTER
          wrap = FlexWrap.WRAP
          spacing = "var(--dwc-space-l)"
          width = 50.percent
          classNames += "row"

          email = textField("E-mail:", "turner.jason@email.com", type = Type.EMAIL) {
            width = 100.percent
          }
        }
        flexLayout {
          justifyContent = FlexJustifyContent.END
          wrap = FlexWrap.WRAP
          spacing = "var(--dwc-space-l)"
          width = 50.percent
          classNames + "row" + "buttons"

          button("Submit", ButtonTheme.PRIMARY) {
            onClick {
              showMessageDialog("Welcome to the app ${firstName.text} ${lastName.text}!", "Welcome")
            }
          }
          button("Clear", ButtonTheme.DEFAULT) {
            onClick {
              firstName.text = ""
              lastName.text = ""
              email.text = ""
            }
          }
        }
      }
  }
}
