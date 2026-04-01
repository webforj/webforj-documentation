package com.webforj.samples.views.fields.textfield

import com.webforj.component.Composite
import com.webforj.component.field.TextField
import com.webforj.component.layout.flexlayout.FlexDirection
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.component.layout.flexlayout.FlexWrap
import com.webforj.kotlin.dsl.component.field.textField
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Text Field Form")
class TextFieldKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      margin = "var(--dwc-space-l)"
      spacing = "var(--dwc-space-l)"
      direction = FlexDirection.ROW
      wrap = FlexWrap.WRAP
      textField("Enter Name", "John Doe", "Name", TextField.Type.TEXT)
      textField("Enter Email", "example@email.com", "Email", TextField.Type.EMAIL)
      textField("Enter Phone Number", "(123) 456-7890", "Phone Number", TextField.Type.TEL)
      textField("Enter URL", "https://www.example.com", "URL", TextField.Type.URL)
      textField("Enter Your Search", "Search...", "Search", TextField.Type.SEARCH)
    }
  }
}