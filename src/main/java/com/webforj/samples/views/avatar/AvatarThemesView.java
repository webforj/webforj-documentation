package com.webforj.samples.views.avatar;

import com.webforj.component.Composite;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Avatar Themes")
public class AvatarThemesView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  FlexLayout filled = new FlexLayout();
  FlexLayout outlined = new FlexLayout();

  public AvatarThemesView() {
    self.setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-m)")
        .setMargin("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER)
        .add(filled, outlined);

    filled.setSpacing("var(--dwc-space-m)")
        .setAlignment(FlexAlignment.CENTER)
        .add(
        new Avatar("Default").setTheme(AvatarTheme.DEFAULT),
        new Avatar("Gray").setTheme(AvatarTheme.GRAY),
        new Avatar("Primary").setTheme(AvatarTheme.PRIMARY),
        new Avatar("Success").setTheme(AvatarTheme.SUCCESS),
        new Avatar("Warning").setTheme(AvatarTheme.WARNING),
        new Avatar("Danger").setTheme(AvatarTheme.DANGER),
        new Avatar("Info").setTheme(AvatarTheme.INFO)
    );

    outlined.setSpacing("var(--dwc-space-m)")
        .setAlignment(FlexAlignment.CENTER)
        .add(
        new Avatar("Default").setTheme(AvatarTheme.OUTLINED_DEFAULT),
        new Avatar("Gray").setTheme(AvatarTheme.OUTLINED_GRAY),
        new Avatar("Primary").setTheme(AvatarTheme.OUTLINED_PRIMARY),
        new Avatar("Success").setTheme(AvatarTheme.OUTLINED_SUCCESS),
        new Avatar("Warning").setTheme(AvatarTheme.OUTLINED_WARNING),
        new Avatar("Danger").setTheme(AvatarTheme.OUTLINED_DANGER),
        new Avatar("Info").setTheme(AvatarTheme.OUTLINED_INFO)
    );
  }
}