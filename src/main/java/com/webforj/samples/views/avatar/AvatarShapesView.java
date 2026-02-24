package com.webforj.samples.views.avatar;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.component.avatar.AvatarShape;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Avatar Shapes")
public class AvatarShapesView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();

  public AvatarShapesView() {
    self.setSpacing("var(--dwc-space-l)")
            .setMargin("var(--dwc-space-l)")
            .setAlignment(FlexAlignment.CENTER)
            .add(
        new Avatar("John Doe").setExpanse(AvatarExpanse.XLARGE).setShape(AvatarShape.CIRCLE),
        new Avatar("John Doe").setExpanse(AvatarExpanse.XLARGE).setShape(AvatarShape.SQUARE)
        );
  }
}