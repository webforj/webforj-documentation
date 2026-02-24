package com.webforj.samples.views.drawer;

import com.webforj.annotation.StyleSheet;
import com.webforj.component.Composite;
import com.webforj.component.button.Button;
import com.webforj.component.button.ButtonTheme;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.drawer.Drawer.Placement;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.Icon;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexAlignment;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexJustifyContent;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.component.layout.flexlayout.FlexWrap;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@StyleSheet("ws://css/drawer/drawerContact.css")
@Route
@FrameTitle("Contact Picker")
public class DrawerContactView extends Composite<FlexLayout> {
  private FlexLayout self = getBoundComponent();

  public DrawerContactView() {
    Drawer drawer = new Drawer()
            .setLabel("Contacts")
            .setPlacement(Placement.BOTTOM_CENTER)
            .open();

    FlexLayout list = new FlexLayout()
            .addClassName("contact-list")
            .setDirection(FlexDirection.COLUMN);

    list.add(createContact("Gregory Baldrake", "US - Albuquerque", "GB", "#fdca8b"));
    list.add(createContact("Betsy Heebink", "US - Madison", "BH", "#85cf8a"));
    list.add(createContact("Wesley Osborn", "US - Seattle", "WO", "#d4df4a"));
    list.add(createContact("Harry Chuckie", "US - Palm Springs", "HC", "#00ffb7"));
    list.add(createContact("Stephanie McIntyre", "US - Modesto", "SM", "#ff6230"));
    list.add(createContact("Dave Strum", "US - Hagerstown", "DS", "#b88bfa"));
    list.add(createContact("Dr. Jane Booker", "US - Hagerstown", "DB", "#4c7c4b"));


    Button openDrawerButton = new Button("Open Contacts");
    openDrawerButton.onClick(e -> drawer.open());

    drawer.add(list);
    self.setMargin("var(--dwc-space-l)")
            .add(openDrawerButton, drawer);
  }

  private FlexLayout createContact(String name, String location, String initials, String color) {
    FlexLayout avatar = new FlexLayout()
            .addClassName("contact-avatar")
            .setText(initials)
            .setStyle("background-color", color);

    Paragraph namePara = new Paragraph(name).addClassName("contact-name");
    Paragraph locationPara = new Paragraph(location).addClassName("contact-location");

    FlexLayout textBlock = new FlexLayout(namePara, locationPara)
            .addClassName("contact-text")
            .setDirection(FlexDirection.COLUMN);

    Icon phoneIcon = TablerIcon.create("phone");
    Button callButton = new Button()
            .addClassName("contact-call")
            .setTheme(ButtonTheme.DEFAULT);
    callButton.setIcon(phoneIcon);

    return new FlexLayout(avatar, textBlock, callButton)
            .addClassName("contact-row")
            .setWrap(FlexWrap.NOWRAP)
            .setAlignment(FlexAlignment.AUTO)
            .setJustifyContent(FlexJustifyContent.EVENLY);
  }
}