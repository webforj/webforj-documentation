package com.webforj.samples.views.button;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.html.elements.Img;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Button Icon")
public class ButtonIconView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();
  private final Button notifications;
  private final Button link;
  private final Button imgButton;

  public ButtonIconView() {
    notifications = new Button("Notifications").setPrefixComponent(TablerIcon.create("bell"));

    link = new Button("Search").setSuffixComponent(TablerIcon.create("external-link"));

    Img imgIcon =
        new Img("https://documentation.webforj.com/img/webforj.svg").setSize("100px", "30px");
    imgButton = new Button(imgIcon);

    self.setSpacing("var(--dwc-space-l)")
        .setMargin("var(--dwc-space-l)")
        .setStyle("flex-wrap", "wrap")
        .setWidth("100%")
        .add(notifications, link, imgButton);
  }
}
