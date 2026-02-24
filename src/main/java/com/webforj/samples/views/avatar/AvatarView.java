package com.webforj.samples.views.avatar;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Team Members")
@StyleSheet("ws://css/avatar/avatar.css")
public class AvatarView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();

  public AvatarView() {
    self.setDirection(FlexDirection.COLUMN)
        .setMargin("var(--dwc-space-l)");

    Div panel = new Div()
            .addClassName("avatar-demo__panel");
    self.add(panel);

    FlexLayout projectHeader = new FlexLayout()
            .addClassName("avatar-demo__project-header")
            .setDirection(FlexDirection.ROW)
            .setSpacing("var(--dwc-space-m)")
            .setAlignment(FlexAlignment.CENTER);
    panel.add(projectHeader);
    Icon headerIcon = TablerIcon.create("folder");
    H4 projectName = new H4("Project Alpha")
            .addClassName("avatar-demo__project-name");
    projectHeader.add(headerIcon, projectName);

    Span sectionLabel = new Span("Team")
            .addClassName("avatar-demo__section-label");
    panel.add(sectionLabel,
        createMember("Sarah Chen", "Product Lead",
                "ws://img/avatar/avatar1.png", AvatarTheme.SUCCESS),
        createMember("Marcus Johnson", "Developer",
                null, AvatarTheme.SUCCESS),
        createMember("Elena Rodriguez", "Designer",
                "ws://img/avatar/avatar2.png", AvatarTheme.WARNING),
        createMember("David Kim", "Developer",
                null, AvatarTheme.GRAY),
        createInviteMember()
    );
  }

  private FlexLayout createMember(String name, String role,
      String imageUrl, AvatarTheme theme) {
    Avatar avatar;
    if (imageUrl != null) {
      Img avatarImg = new Img(imageUrl, name);
      avatar = new Avatar(name, avatarImg);
    } else {
      avatar = new Avatar(name);
    }
    avatar.setTheme(theme)
            .onClick(e -> showProfileDialog(name, role, imageUrl, theme));

    FlexLayout info = new FlexLayout()
            .setDirection(FlexDirection.COLUMN)
            .addClassName("avatar-demo__info");
    Span nameLabel = new Span(name)
            .addClassName("avatar-demo__name");
    Span roleLabel = new Span(role)
            .addClassName("avatar-demo__role");
    info.add(nameLabel, roleLabel);

    return FlexLayout.create(avatar, info)
        .horizontal()
        .align().center()
        .build()
        .addClassName("avatar-demo__row")
        .setSpacing("var(--dwc-space-m)");
  }

  private FlexLayout createInviteMember() {
    Icon userIcon = TablerIcon.create("user");
    Avatar avatar = new Avatar("", userIcon)
            .setTheme(AvatarTheme.OUTLINED_GRAY);

    Span label = new Span("Invite Member")
            .addClassName("avatar-demo__name");

    return FlexLayout.create(avatar, label)
        .horizontal()
        .align().center()
        .build()
        .addClassName("avatar-demo__row")
        .setSpacing("var(--dwc-space-m)");
  }

  private void showProfileDialog(String name, String role,
      String imageUrl, AvatarTheme theme) {

    Avatar largeAvatar;
    if (imageUrl != null) {
      Img avatarImg = new Img(imageUrl, name);
      largeAvatar = new Avatar(name, avatarImg);
    } else {
      largeAvatar = new Avatar(name);
    }
    largeAvatar.setExpanse(AvatarExpanse.XXLARGE)
            .setTheme(theme);
    H4 nameLabel = new H4(name)
            .setStyle("margin", "0");
    Span roleLabel = new Span(role)
            .setStyle("color", "var(--dwc-color-default-text)");
    Button viewProfile = new Button("View Profile", ButtonTheme.PRIMARY);

    FlexLayout content = new FlexLayout()
              .setDirection(FlexDirection.COLUMN)
              .setAlignment(FlexAlignment.CENTER)
              .setStyle("padding", "var(--dwc-space-l)")
              .setSpacing("var(--dwc-space-s)");
    content.add(largeAvatar, nameLabel, roleLabel, viewProfile);
    Dialog dialog = new Dialog()
              .setMaxWidth("260px");
    dialog.add(content);
    self.add(dialog);
    dialog.open();
  }

}