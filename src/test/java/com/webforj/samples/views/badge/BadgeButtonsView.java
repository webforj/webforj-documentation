package com.webforj.samples.views.badge;

import com.webforj.component.Composite;
import com.webforj.component.badge.Badge;
import com.webforj.component.badge.BadgeExpanse;
import com.webforj.component.badge.BadgeTheme;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.html.elements.H3;
import com.webforj.component.icons.FeatherIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route("/buttons")
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
    row.setStyle("flex-wrap", "wrap");
    row.setStyle("align-items", "center");

    Button notifBtn = new Button("Notifications");
    notifBtn.setPrefixComponent(FeatherIcon.BELL.create());
    notifBtn.setTheme(ButtonTheme.PRIMARY);
    notifBtn.setBadge(new Badge("5").setTheme(BadgeTheme.DANGER)
        .setExpanse(BadgeExpanse.XSMALL));

    Button msgBtn = new Button("Messages");
    msgBtn.setPrefixComponent(FeatherIcon.MAIL.create());
    msgBtn.setTheme(ButtonTheme.DEFAULT);
    msgBtn.setBadge(new Badge("12").setTheme(BadgeTheme.PRIMARY)
        .setExpanse(BadgeExpanse.XSMALL));

    row.add(notifBtn, msgBtn);
    self.add(row);

    self.add(new H3("Button Sizes with Badge"));

    FlexLayout sizeRow = FlexLayout.create().horizontal().build();
    sizeRow.setSpacing("var(--dwc-space-l)");
    sizeRow.setStyle("flex-wrap", "wrap");
    sizeRow.setStyle("align-items", "center");

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
      btn.setBadge(new Badge("3").setTheme(BadgeTheme.DANGER)
          .setExpanse(BadgeExpanse.XSMALL));
      sizeRow.add(btn);
    }

    self.add(sizeRow);
  }
}
