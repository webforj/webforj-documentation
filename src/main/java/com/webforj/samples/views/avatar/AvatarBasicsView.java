package com.webforj.samples.views.avatar;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.html.elements.Div;
import com.webforj.component.html.elements.H4;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.html.elements.Span;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.avatar.Avatar;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Avatar Basics")
@StyleSheet("ws://css/avatar/avatar.css")
public class AvatarBasicsView extends Composite<FlexLayout> {

  public AvatarBasicsView() {
    FlexLayout self = getBoundComponent();
    self.setDirection(FlexDirection.COLUMN)
        .setMargin("var(--dwc-space-l)");

    Div panel = new Div();
    panel.addClassName("avatar-demo__panel");

    FlexLayout header = new FlexLayout(TablerIcon.create("folder"), new H4("Project Alpha"));
    header.addClassName("avatar-demo__project-header")
        .setSpacing("var(--dwc-space-m)")
        .setAlignment(FlexAlignment.CENTER);

    Span teamLabel = new Span("Team");
    teamLabel.addClassName("avatar-demo__section-label");

    Avatar avatar1 = new Avatar("John Doe");
    Avatar avatar2 = new Avatar("Sarah Chen");
    Avatar avatar3 = new Avatar("Alex Smith", "A");

    panel.add(header, teamLabel);
    panel.add(createRow(avatar1, "John Doe"));
    panel.add(createRow(avatar2, "Sarah Chen"));
    panel.add(createRow(avatar3, "Alex Smith"));

    self.add(panel);
  }

  private FlexLayout createRow(Avatar avatar, String name) {
    return FlexLayout.create(avatar, new Paragraph(name))
        .horizontal()
        .align().center()
        .build()
        .addClassName("avatar-demo__row")
        .setSpacing("var(--dwc-space-m)");
  }
}