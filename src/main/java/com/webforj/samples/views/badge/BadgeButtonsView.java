package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.Expanse;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeExpanse;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Badge - Buttons")
public class BadgeButtonsView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public BadgeButtonsView() {
    self.setDirection(FlexDirection.COLUMN)
      .setSpacing("var(--dwc-space-m)")
      .setPadding("var(--dwc-space-l)")
      .setMargin("0 auto")
      .setMaxWidth("700px")
      .add(new H3("Badge on Buttons"),
        createNotificationRow(),
        new H3("Button Sizes with Badge"),
        createButtonSizesRow());
  }

  private FlexLayout createNotificationRow() {
    Button notifBtn = new Button("Notifications")
      .setPrefixComponent(FeatherIcon.BELL.create())
      .setTheme(ButtonTheme.PRIMARY)
      .setBadge(createBadge("5", BadgeTheme.DANGER, BadgeExpanse.XSMALL));

    Button msgBtn = new Button("Messages")
      .setPrefixComponent(FeatherIcon.MAIL.create())
      .setTheme(ButtonTheme.DEFAULT)
      .setBadge(createBadge("12", BadgeTheme.PRIMARY, BadgeExpanse.XSMALL));

    return FlexLayout.create(notifBtn, msgBtn)
      .horizontal()
      .wrap()
      .build()
      .setSpacing("var(--dwc-space-l)")
      .setAlignment(FlexAlignment.CENTER);
  }

  private FlexLayout createButtonSizesRow() {
    FlexLayout row = FlexLayout.create()
      .horizontal()
      .wrap()
      .build()
      .setSpacing("var(--dwc-space-l)")
      .setAlignment(FlexAlignment.CENTER);

    for (Expanse expanse : Expanse.values()) {
      String size = expanse.name()
        .toLowerCase()
        .transform(s -> s.startsWith("x") ? s.substring(0, 2) : s.substring(0, 1));

      Button btn = new Button(size)
        .setTheme(ButtonTheme.PRIMARY)
        .setExpanse(expanse)
        .setBadge(createBadge("3", BadgeTheme.DANGER, BadgeExpanse.XSMALL));
      
      row.add(btn);
    }

    return row;
  }

  private Badge createBadge(String text, BadgeTheme theme, BadgeExpanse expanse) {
    return new Badge(text)
      .setTheme(theme)
      .setExpanse(expanse);
  }
}
