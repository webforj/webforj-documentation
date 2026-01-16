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

  public AvatarView() {
    FlexLayout self = getBoundComponent();
    self.setDirection(FlexDirection.COLUMN)
        .setMargin("var(--dwc-space-l)");

    Div panel = new Div();
    panel.addClassName("avatar-demo__panel");

    FlexLayout projectHeader = new FlexLayout();
    projectHeader.addClassName("avatar-demo__project-header")
        .setDirection(FlexDirection.ROW)
        .setSpacing("var(--dwc-space-m)")
        .setAlignment(FlexAlignment.CENTER);

    H4 projectName = new H4("Project Alpha");
    projectName.addClassName("avatar-demo__project-name");

    projectHeader.add(TablerIcon.create("folder"), projectName);

    Span sectionLabel = new Span("Team");
    sectionLabel.addClassName("avatar-demo__section-label");

    panel.add(projectHeader);
    panel.add(sectionLabel);
    panel.add(createMember("Sarah Chen", "Product Lead",
        "ws://img/avatar/avatar1.png", AvatarTheme.SUCCESS));
    panel.add(createMember("Marcus Johnson", "Developer",
        null, AvatarTheme.SUCCESS));
    panel.add(createMember("Elena Rodriguez", "Designer",
        "ws://img/avatar/avatar2.png", AvatarTheme.WARNING));
    panel.add(createMember("David Kim", "Developer",
        null, AvatarTheme.GRAY));
    panel.add(createInviteMember());

    self.add(panel);
  }

  private FlexLayout createMember(String name, String role,
      String imageUrl, AvatarTheme theme) {
    Avatar avatar;
    if (imageUrl != null) {
      avatar = new Avatar(name, new Img(imageUrl, name));
    } else {
      avatar = new Avatar(name);
    }
    avatar.setTheme(theme);
    avatar.onClick(e -> showProfileDialog(name, role, imageUrl, theme));

    FlexLayout info = new FlexLayout();
    info.setDirection(FlexDirection.COLUMN)
        .addClassName("avatar-demo__info");
    Span nameLabel = new Span(name);
    nameLabel.addClassName("avatar-demo__name");
    Span roleLabel = new Span(role);
    roleLabel.addClassName("avatar-demo__role");
    info.add(nameLabel, roleLabel);

    return FlexLayout.create(avatar, info)
        .horizontal()
        .align().center()
        .build()
        .addClassName("avatar-demo__row")
        .setSpacing("var(--dwc-space-m)");
  }

  private FlexLayout createInviteMember() {
    Avatar avatar = new Avatar("", TablerIcon.create("user"));
    avatar.setTheme(AvatarTheme.OUTLINED_GRAY);

    Span label = new Span("Invite Member");
    label.addClassName("avatar-demo__name");

    return FlexLayout.create(avatar, label)
        .horizontal()
        .align().center()
        .build()
        .addClassName("avatar-demo__row")
        .setSpacing("var(--dwc-space-m)");
  }

  private void showProfileDialog(String name, String role,
      String imageUrl, AvatarTheme theme) {
    Dialog dialog = new Dialog();
    dialog.setMaxWidth("260px");

    Avatar largeAvatar;
    if (imageUrl != null) {
      largeAvatar = new Avatar(name, new Img(imageUrl, name));
    } else {
      largeAvatar = new Avatar(name);
    }
    largeAvatar.setExpanse(AvatarExpanse.XXLARGE);
    largeAvatar.setTheme(theme);

    FlexLayout content = new FlexLayout();
    content.setDirection(FlexDirection.COLUMN)
        .setAlignment(FlexAlignment.CENTER)
        .setStyle("padding", "var(--dwc-space-l)")
        .setSpacing("var(--dwc-space-s)");

    H4 nameLabel = new H4(name);
    nameLabel.setStyle("margin", "0");
    Span roleLabel = new Span(role);
    roleLabel.setStyle("color", "var(--dwc-color-default-text)");

    Button viewProfile = new Button("View Profile", ButtonTheme.PRIMARY);

    content.add(largeAvatar, nameLabel, roleLabel, viewProfile);
    dialog.add(content);
    getBoundComponent().add(dialog);
    dialog.open();
  }

}