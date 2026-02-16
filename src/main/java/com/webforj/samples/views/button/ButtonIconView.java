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
  private FlexLayout self = getBoundComponent();
  private Button notifications;
  private Button link;
  private Button imgButton;

  public ButtonIconView() {
    self.setSpacing("var(--dwc-space-l)")
            .setMargin("var(--dwc-space-l)")
            .setStyle("flex-wrap", "wrap")
            .setWidth("100%")
            .add(notifications, link, imgButton);

    notifications = new Button("Notifications")
            .setPrefixComponent(TablerIcon.create("bell"));

    link = new Button("Search")
            .setSuffixComponent(TablerIcon.create("external-link"));

    Img imgIcon = new Img("https://documentation.webforj.com/img/webforj.svg")
            .setSize("100px", "30px");
    imgButton = new Button(imgIcon);
  }
}
