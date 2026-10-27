package com.webforj.samples.views.avatar;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.card.Card;
import com.webforj.component.card.Card.Shadow;
import com.webforj.component.dialog.Dialog;
import com.webforj.component.html.elements.H3;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Img;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Team Members")
@BundleEntry("css/avatar/avatar.css")
public class AvatarView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Card panel = new Card();
  private final H3 panelHeader = new H3("Project Alpha");
  private final Span sectionLabel = new Span("Team");

  public AvatarView() {
    self.setDirection(FlexDirection.COLUMN)
        .setMargin("var(--dwc-space-l)")
        .setAlignment(FlexAlignment.CENTER)
        .add(panel);
    setupPanel();
    addMembers();
  }

  private void setupPanel() {
    panel
        .setWidth(300)
        .addToIcon(TablerIcon.create("folder"))
        .addToTitle(panelHeader)
        .setDivided(true)
        .addToBody(sectionLabel.addClassName("avatar-demo__section-label"));
  }

  private void addMembers() {
    panel.add(
        createMember(
            "Sarah Chen", "Product Lead", "ws://img/avatar/avatar1.png", AvatarTheme.SUCCESS));
    panel.add(createMember("Marcus Johnson", "Developer", null, AvatarTheme.SUCCESS));
    panel.add(
        createMember(
            "Elena Rodriguez", "Designer", "ws://img/avatar/avatar2.png", AvatarTheme.WARNING));
    panel.add(createMember("David Kim", "Developer", null, AvatarTheme.GRAY));
    panel.add(createInviteMember());
  }

  private Card createMember(String name, String role, String imageUrl, AvatarTheme theme) {
    Card card = new Card();
    Avatar avatar = imageUrl != null ? new Avatar(name, new Img(imageUrl, name)) : new Avatar(name);
    avatar.setTheme(theme);
    card.setBorderless(true)
        .setShadow(Shadow.NONE)
        .addClassName("card-hover")
        .addToIcon(avatar)
        .addToTitle(new H4(name))
        .addToCaption(new Span(role))
        .onClick(e -> showProfileDialog(name, role, imageUrl, theme));

    return card;
  }

  private Card createInviteMember() {
    Card card = new Card();
    Avatar avatar = new Avatar("", TablerIcon.create("user")).setTheme(AvatarTheme.OUTLINED_GRAY);
    card.setBorderless(true)
        .setShadow(Shadow.NONE)
        .addClassName("card-hover")
        .addToIcon(avatar)
        .addToTitle(new H4("New User"))
        .addToCaption(new Span("Invite a Member"));

    return card;
  }

  private void showProfileDialog(String name, String role, String imageUrl, AvatarTheme theme) {
    Avatar largeAvatar =
        imageUrl != null ? new Avatar(name, new Img(imageUrl, name)) : new Avatar(name);
    largeAvatar.setExpanse(AvatarExpanse.XXLARGE).setTheme(theme);

    H4 nameLabel = new H4(name).setStyle("margin", "0");
    Span roleLabel = new Span(role).setStyle("color", "var(--dwc-color-default-text)");

    Button viewProfile = new Button("View Profile", ButtonTheme.PRIMARY);

    FlexLayout content =
        FlexLayout.create(largeAvatar, nameLabel, roleLabel, viewProfile)
            .vertical()
            .align()
            .center()
            .build()
            .setStyle("padding", "var(--dwc-space-l)")
            .setSpacing("var(--dwc-space-s)");

    Dialog dialog = new Dialog().setMaxWidth("260px").open();
    dialog.add(content);

    self.add(dialog);
  }
}
