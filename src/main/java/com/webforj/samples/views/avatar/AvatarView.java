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

import java.util.Objects;

@Route
@FrameTitle("Team Members")
@StyleSheet("ws://css/avatar/avatar.css")
public class AvatarView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();
  private Div panel = new Div();

  public AvatarView() {
    self.setDirection(FlexDirection.COLUMN)
            .setMargin("var(--dwc-space-l)")
            .add(panel);
    setupPanel();
    addMembers();
  }

  private void setupPanel() {
    panel.addClassName("avatar-demo__panel");

    FlexLayout projectHeader = new FlexLayout()
            .addClassName("avatar-demo__project-header")
            .setDirection(FlexDirection.ROW)
            .setSpacing("var(--dwc-space-m)")
            .setAlignment(FlexAlignment.CENTER);

    H4 projectName = new H4("Project Alpha")
            .addClassName("avatar-demo__project-name");

    projectHeader.add(TablerIcon.create("folder"), projectName);

    Span sectionLabel = new Span("Team")
            .addClassName("avatar-demo__section-label");

    panel.add(projectHeader);
    panel.add(sectionLabel);
  }

  private void addMembers() {
    panel.add(createMember("Sarah Chen", "Product Lead",
            "ws://img/avatar/avatar1.png", AvatarTheme.SUCCESS));
    panel.add(createMember("Marcus Johnson", "Developer",
            null, AvatarTheme.SUCCESS));
    panel.add(createMember("Elena Rodriguez", "Designer",
            "ws://img/avatar/avatar2.png", AvatarTheme.WARNING));
    panel.add(createMember("David Kim", "Developer",
            null, AvatarTheme.GRAY));
    panel.add(createInviteMember());
  }

  private FlexLayout createMember(String name, String role,
                                  String imageUrl, AvatarTheme theme) {
    Avatar avatar = imageUrl != null
            ? new Avatar(name, new Img(imageUrl, name))
            : new Avatar(name);
    avatar.setTheme(theme)
            .onClick(e -> showProfileDialog(name, role, imageUrl, theme));

    Span nameLabel = new Span(name)
            .addClassName("avatar-demo__name");
    Span roleLabel = new Span(role)
            .addClassName("avatar-demo__role");

    FlexLayout info = FlexLayout.create(nameLabel, roleLabel)
            .vertical()
            .build()
            .addClassName("avatar-demo__info");

    return FlexLayout.create(avatar, info)
            .horizontal()
            .align().center()
            .build()
            .addClassName("avatar-demo__row")
            .setSpacing("var(--dwc-space-m)");
  }

  private FlexLayout createInviteMember() {
    Avatar avatar = new Avatar("", TablerIcon.create("user"))
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
    Avatar largeAvatar = imageUrl != null
            ? new Avatar(name, new Img(imageUrl, name))
            : new Avatar(name);
    largeAvatar.setExpanse(AvatarExpanse.XXLARGE)
            .setTheme(theme);

    H4 nameLabel = new H4(name)
            .setStyle("margin", "0");
    Span roleLabel = new Span(role)
            .setStyle("color", "var(--dwc-color-default-text)");

    Button viewProfile = new Button("View Profile", ButtonTheme.PRIMARY);

    FlexLayout content = FlexLayout.create(largeAvatar, nameLabel, roleLabel, viewProfile)
            .vertical()
            .align().center()
            .build()
            .setStyle("padding", "var(--dwc-space-l)")
            .setSpacing("var(--dwc-space-s)");

    Dialog dialog = new Dialog()
            .setMaxWidth("260px")
            .open();
    dialog.add(content);

    self.add(dialog);
  }

}