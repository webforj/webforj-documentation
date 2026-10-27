package com.webforj.samples.views.drawer;

import com.webforj.bundle.annotation.BundleEntry;
import com.webforj.component.Composite;
import com.webforj.component.avatar.Avatar;
import com.webforj.component.avatar.AvatarExpanse;
import com.webforj.component.avatar.AvatarTheme;
import com.webforj.component.button.Button;
import com.webforj.component.card.Card;
import com.webforj.component.card.Card.Shadow;
import com.webforj.component.drawer.Drawer;
import com.webforj.component.drawer.Drawer.Placement;
import com.webforj.component.html.elements.Paragraph;
import com.webforj.component.icons.IconButton;
import com.webforj.component.icons.TablerIcon;
import com.webforj.component.layout.flexlayout.FlexDirection;
import com.webforj.component.layout.flexlayout.FlexLayout;
import com.webforj.router.annotation.FrameTitle;
import com.webforj.router.annotation.Route;

@BundleEntry("css/drawer/drawerContact.css")
@Route
@FrameTitle("Contact Picker")
public class DrawerContactView extends Composite<FlexLayout> {
  private final FlexLayout self = getBoundComponent();

  public DrawerContactView() {
    Drawer drawer =
        new Drawer()
            .setLabel("Contacts")
            .setPlacement(Placement.BOTTOM_CENTER)
            .addClassName("contact-drawer")
            .open();

    FlexLayout list = new FlexLayout().setDirection(FlexDirection.COLUMN).setSpacing("0px");

    list.add(createContact("Gregory Baldrake", "US - Albuquerque", AvatarTheme.DANGER));
    list.add(createContact("Betsy Heebink", "US - Madison", AvatarTheme.DEFAULT));
    list.add(createContact("Wesley Osborn", "US - Seattle", AvatarTheme.INFO));
    list.add(createContact("Harry Chuckie", "US - Palm Springs", AvatarTheme.PRIMARY));
    list.add(createContact("Stephanie McIntyre", "US - Modesto", AvatarTheme.SUCCESS));
    list.add(createContact("Dave Strum", "US - Hagerstown", AvatarTheme.WARNING));
    list.add(createContact("Jane Booker", "US - Hagerstown", AvatarTheme.GRAY));

    Button openDrawerButton = new Button("Open Contacts");
    openDrawerButton.onClick(e -> drawer.open());

    drawer.add(list);
    self.setMargin("var(--dwc-space-l)").add(openDrawerButton, drawer);
  }

  private Card createContact(String name, String location, AvatarTheme theme) {

    Avatar avatar =
        new Avatar(name)
            .setTheme(theme)
            .addClassName("avatar-margin")
            .setExpanse(AvatarExpanse.LARGE);

    Paragraph namePara = new Paragraph(name);
    Paragraph locationPara = new Paragraph(location);

    IconButton callButton = new IconButton(TablerIcon.create("phone"));

    Card contactCard =
        new Card()
            .addClassName("contact-row")
            .setShadow(Shadow.NONE)
            .setOrientation(Card.Orientation.HORIZONTAL)
            .setBorderless(true)
            .setWidth("100%")
            .addToIcon(avatar)
            .addToTitle(namePara)
            .addToCaption(locationPara)
            .addToHeaderActions(callButton);

    return contactCard;
  }
}
