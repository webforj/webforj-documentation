package com.webforj.samples.views.avatar;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Avatar Expanses")
public class AvatarExpansesView extends Composite<FlexLayout> {

  public AvatarExpansesView() {
    FlexLayout self = getBoundComponent();
    self.setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER);

    self.add(
        new Avatar("John Doe").setExpanse(AvatarExpanse.XXXSMALL),
        new Avatar("John Doe").setExpanse(AvatarExpanse.XXSMALL),
        new Avatar("John Doe").setExpanse(AvatarExpanse.XSMALL),
        new Avatar("John Doe").setExpanse(AvatarExpanse.SMALL),
        new Avatar("John Doe").setExpanse(AvatarExpanse.MEDIUM),
        new Avatar("John Doe").setExpanse(AvatarExpanse.LARGE),
        new Avatar("John Doe").setExpanse(AvatarExpanse.XLARGE),
        new Avatar("John Doe").setExpanse(AvatarExpanse.XXLARGE),
        new Avatar("John Doe").setExpanse(AvatarExpanse.XXXLARGE)
    );
  }
}