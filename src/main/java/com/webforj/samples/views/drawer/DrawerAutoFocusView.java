package com.webforj.samples.views.drawer;

import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.optioninput.CheckBox;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@Route
@FrameTitle("Drawer AutoFocus")
public class DrawerAutoFocusView extends Composite<FlexLayout> {

  FlexLayout layout = getBoundComponent();
  Drawer drawer = new Drawer();

  public DrawerAutoFocusView() {
    layout.setMargin("var(--dwc-space-m)");

    drawer.setAutoFocus(true);

    CheckBox emailNotifications = new CheckBox("Email Notifications");
    CheckBox smsNotifications = new CheckBox("SMS Notifications");
    CheckBox pushNotifications = new CheckBox("Push Notifications");

    FlexLayout checkBoxContainer = new FlexLayout(emailNotifications, smsNotifications, pushNotifications)
        .setDirection(FlexDirection.COLUMN)
        .setSpacing("var(--dwc-space-s)");

    Button saveButton = new Button("Save Preferences")
        .setTheme(ButtonTheme.PRIMARY)
        .setWidth("100%");

    drawer.add(checkBoxContainer);
    drawer.addToFooter(saveButton);

    Button openDrawerButton = new Button("Open Preferences");
    openDrawerButton.onClick(e -> drawer.open());

    layout.add(openDrawerButton, drawer);

    drawer.setLabel("Notification Preferences");
    drawer.open();
  }
}
