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
  private final FlexLayout self = getBoundComponent();

  public AvatarExpansesView() {
    self.setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER);

    // Iterate through AvatarExpanse values in reverse order (excluding NONE)
    AvatarExpanse[] expanses = AvatarExpanse.values();
    for (int i = expanses.length - 1; i >= 0; i--) {
      AvatarExpanse expanse = expanses[i];
      if (expanse != AvatarExpanse.NONE) {
        self.add(new Avatar("John Doe").setExpanse(expanse));
      }
    }
  }
}
