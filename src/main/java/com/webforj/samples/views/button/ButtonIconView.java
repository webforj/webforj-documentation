package com.webforj.samples.views.button;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Img;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.icons.Icon;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

/**
 * Demo application to display icon addition to buttons.
 */
@Route
@FrameTitle("Button Icon")
public class ButtonIconView extends Composite<FlexLayout> {
  FlexLayout self = getBoundComponent();
  Button notifications;
  Button link;
  Button imgButton;
  Icon notificationsIcon;
  Icon settingsIcon;
  Icon linkIcon;
  Img imgIcon;

  public ButtonIconView() {
    self.setSpacing("var(--dwc-space-l)").setMargin("var(--dwc-space-l)").setStyle("flex-wrap", "wrap")
        .setWidth("100%");

    notificationsIcon = TablerIcon.create("bell");
    notifications = new Button("Notifications");
    notifications.setPrefixComponent(notificationsIcon);

    linkIcon = TablerIcon.create("external-link");
    link = new Button("Search");
    link.setSuffixComponent(linkIcon);

    imgIcon = new Img("https://documentation.webforj.com/img/webforj.svg");
    imgIcon.setSize("100px", "30px");
    imgButton = new Button(imgIcon);

    self.add(notifications, link, imgButton);
  }
}
