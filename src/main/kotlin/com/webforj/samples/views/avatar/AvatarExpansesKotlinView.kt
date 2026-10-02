package com.webforj.samples.views.avatar

import com.webforj.component.Composite
import com.webforj.component.avatar.AvatarExpanse
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.avatar.avatar
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Avatar Expanses")
class AvatarExpansesKotlinView: Composite<FlexLayout>() {
  private val self = boundComponent

  init {
      self.apply {
        spacing = "var(--dwc-space-m)"
        margin = "var(--dwc-space-l)"
        alignment = FlexAlignment.CENTER

        AvatarExpanse.entries.reversed()
          .filter { it != AvatarExpanse.NONE }
          .forEach {
          avatar("John Doe") {
            expanse = it
          }
        }
      }
  }
}
