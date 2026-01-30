package com.webforj.samples.views.fields.passwordfield

import com.webforj.component.Composite
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.field.passwordField
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Password Field Demo")
class PasswordFieldKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
    self.apply {
      margin = "var(--dwc-space-m)"
      passwordField("Enter your Password:", placeholder = "Password")
    }
  }

}