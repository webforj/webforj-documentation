package com.webforj.samples.views.avatar

import com.webforj.component.Composite
import com.webforj.component.avatar.AvatarExpanse
import com.webforj.component.avatar.AvatarShape
import com.webforj.component.layout.flexlayout.FlexAlignment
import com.webforj.component.layout.flexlayout.FlexLayout
import com.webforj.kotlin.dsl.component.avatar.avatar
import com.webforj.router.annotation.FrameTitle
import com.webforj.router.annotation.Route

@Route
@FrameTitle("Avatar Shapes")
class AvatarShapesKotlinView: Composite<FlexLayout>() {

  init {
      boundComponent.apply {
        spacing = "var(--dwc-space-l)"
        margin = "var(--dwc-space-l)"
        alignment = FlexAlignment.CENTER
        avatar("John Doe") {
          expanse = AvatarExpanse.XLARGE
          shape = AvatarShape.CIRCLE
        }
        avatar("John Doe") {
          expanse = AvatarExpanse.XLARGE
          shape = AvatarShape.SQUARE
        }
      }
  }
}
