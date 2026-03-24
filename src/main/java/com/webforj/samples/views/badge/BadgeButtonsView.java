package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
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
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Badge - Buttons")
public class BadgeButtonsView extends Composite<FlexLayout> {

  private final FlexLayout self = getBoundComponent();

  public BadgeButtonsView() {
    self.setDirection(FlexDirection.COLUMN);
    self.setMaxWidth("700px");
    self.setStyle("margin", "0 auto");
    self.setPadding("var(--dwc-space-l)");
    self.setSpacing("var(--dwc-space-m)");

    self.add(new H3("Badge on Buttons"));

    FlexLayout row = FlexLayout.create().horizontal().build();
    row.setSpacing("var(--dwc-space-l)");
    row.setWrap(FlexWrap.WRAP);
    row.setAlignment(FlexAlignment.CENTER);

    Badge notifBadge = new Badge("5");
    notifBadge.setTheme(BadgeTheme.DANGER);
    notifBadge.setExpanse(BadgeExpanse.XSMALL);

    Button notifBtn = new Button("Notifications");
    notifBtn.setPrefixComponent(FeatherIcon.BELL.create());
    notifBtn.setTheme(ButtonTheme.PRIMARY);
    notifBtn.setBadge(notifBadge);

    Badge msgBadge = new Badge("12");
    msgBadge.setTheme(BadgeTheme.PRIMARY);
    msgBadge.setExpanse(BadgeExpanse.XSMALL);

    Button msgBtn = new Button("Messages");
    msgBtn.setPrefixComponent(FeatherIcon.MAIL.create());
    msgBtn.setTheme(ButtonTheme.DEFAULT);
    msgBtn.setBadge(msgBadge);

    row.add(notifBtn, msgBtn);
    self.add(row);

    self.add(new H3("Button Sizes with Badge"));

    FlexLayout sizeRow = FlexLayout.create().horizontal().build();
    sizeRow.setSpacing("var(--dwc-space-l)");
    sizeRow.setWrap(FlexWrap.WRAP);
    sizeRow.setAlignment(FlexAlignment.CENTER);

    String[] sizes = {"xs", "s", "m", "l", "xl"};
    for (String size : sizes) {
      Button btn = new Button(size);
      btn.setTheme(ButtonTheme.PRIMARY);
      btn.setExpanse(com.webforj.component.Expanse.valueOf(
          switch (size) {
            case "xs" -> "XSMALL";
            case "s" -> "SMALL";
            case "m" -> "MEDIUM";
            case "l" -> "LARGE";
            case "xl" -> "XLARGE";
            default -> "MEDIUM";
          }));

      Badge sizeBadge = new Badge("3");
      sizeBadge.setTheme(BadgeTheme.DANGER);
      sizeBadge.setExpanse(BadgeExpanse.XSMALL);
      btn.setBadge(sizeBadge);
      sizeRow.add(btn);
    }

    self.add(sizeRow);
  }
}
