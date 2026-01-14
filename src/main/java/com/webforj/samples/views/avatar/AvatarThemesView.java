package com.webforj.samples.views.avatar;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Avatar Themes")
public class AvatarThemesView extends Composite<FlexLayout> {

  public AvatarThemesView() {
    FlexLayout self = getBoundComponent();
    self.setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER);

    self.add(
        new Avatar("Default").setTheme(AvatarTheme.DEFAULT),
        new Avatar("Gray").setTheme(AvatarTheme.GRAY),
        new Avatar("Primary").setTheme(AvatarTheme.PRIMARY),
        new Avatar("Success").setTheme(AvatarTheme.SUCCESS),
        new Avatar("Warning").setTheme(AvatarTheme.WARNING),
        new Avatar("Danger").setTheme(AvatarTheme.DANGER),
        new Avatar("Info").setTheme(AvatarTheme.INFO)
    );
  }
}